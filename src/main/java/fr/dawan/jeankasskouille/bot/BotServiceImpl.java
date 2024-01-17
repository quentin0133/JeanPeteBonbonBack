package fr.dawan.jeankasskouille.bot;

import fr.dawan.jeankasskouille.bot.enums.BotCommand;
import fr.dawan.jeankasskouille.bot.exceptions.DateTimeBeforeException;
import fr.dawan.jeankasskouille.bot.exceptions.NoTextChannelException;
import fr.dawan.jeankasskouille.generic.BaseEntity;
import fr.dawan.jeankasskouille.guildregistration.GuildRegistration;
import fr.dawan.jeankasskouille.guildregistration.GuildRegistrationRepository;
import fr.dawan.jeankasskouille.schedule.Schedule;
import fr.dawan.jeankasskouille.schedule.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class BotServiceImpl extends ListenerAdapter implements BotService {
    private final ScheduleRepository scheduleRepository;
    private final GuildRegistrationRepository guildRegistrationRepository;

    private ScheduledExecutorService scheduledActivity;

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        event.getGuild().updateCommands().addCommands(
            Arrays.stream(BotCommand.values())
                .map(botCommand ->
                    Commands.slash(botCommand.getActionName(), botCommand.getDescription())
                        .addOptions(botCommand.getOptionsData())
                ).toList()
        ).queue();

        try {
            updateEvents(event.getGuild());
        }
        catch (NoTextChannelException e) {
            Arrays.stream(e.getStackTrace()).forEach(stackTraceElement -> log.error(stackTraceElement.toString()));
        }
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        ReplyCallbackAction replyCallback = event.reply("Erreur interne, désolé pour la confusion");
        Guild guild = event.getGuild();
        long idGuild = Objects.requireNonNull(guild).getIdLong();
        try {
            BotCommand command = BotCommand.valueOf(event.getName().toUpperCase());
            switch (command) {
                case ASSIGN -> {
                    guildRegistrationRepository.saveAndFlush(
                        new GuildRegistration(idGuild, event.getChannel().asTextChannel().getIdLong())
                    );
                    replyCallback = event.reply("Ce canal a été assigné pour les notifications");
                }
                case UPDATE -> {
                    updateEvents(guild);
                    replyCallback = event.reply("Les évènements ont été mis à jour");
                }
                case SET_HOUR -> {
                    String message = Objects.requireNonNull(event.getOption("message")).getAsString();
                    String time = Objects.requireNonNull(event.getOption("time")).getAsString();

                    replyCallback = setEvent(message, LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), time, event);
                }
                case SET_DATE_HOUR -> {
                    String message = Objects.requireNonNull(event.getOption("message")).getAsString();
                    String date = Objects.requireNonNull(event.getOption("date")).getAsString();
                    String time = Objects.requireNonNull(event.getOption("time")).getAsString();

                    replyCallback = setEvent(message, date, time, event);
                }
                default -> throw new IllegalArgumentException("Cette commande n'a pas encore été implémenter");
            }
        }
        catch (DateTimeBeforeException dtbe) {
            replyCallback = event.reply("La date ne doit pas dépassé la date courante").setEphemeral(true);
        }
        catch (DateTimeParseException dte) {
            replyCallback = event.reply("La date n'a pas respecté le format demandé").setEphemeral(true);
        }
        catch (Exception e) {
            replyCallback = event.reply(e.getMessage()).setEphemeral(true);
            Arrays.stream(e.getStackTrace()).forEach(stackTraceElement -> log.error(stackTraceElement.toString()));
        } finally {
            replyCallback.queue();
        }
    }

    private ReplyCallbackAction setEvent(String message, String date, String time, SlashCommandInteractionEvent event) throws NoTextChannelException, DateTimeBeforeException {
        Guild guild = event.getGuild();
        GuildRegistration guildRegistration = getGuildRegistration(guild.getIdLong());

        LocalDateTime localDateTime = LocalDateTime.parse("%s %s".formatted(date, time),
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

        if (localDateTime.isBefore(LocalDateTime.now()))
            throw new DateTimeBeforeException();

        Schedule newSchedule = new Schedule(message, localDateTime);
        guildRegistration.getSchedules().add(newSchedule);

        scheduleRepository.saveAndFlush(newSchedule);
        guildRegistrationRepository.saveAndFlush(guildRegistration);

        updateEvents(guild);
        return event.reply("L'évènement a été enregistrer avec succès");
    }

    @Override
    public TextChannel getTextChannelByIdGuild(Guild guild) throws NoTextChannelException {
        return guild.getChannelById(TextChannel.class, getGuildRegistration(guild.getIdLong()).getIdTextChannel());
    }

    @Override
    public GuildRegistration getGuildRegistration(long idGuild) throws NoTextChannelException {
        Optional<GuildRegistration> guildOptional = guildRegistrationRepository.findById(idGuild);
        if (guildOptional.isEmpty())
            throw new NoTextChannelException();
        return guildOptional.get();
    }

    @Override
    public void updateEvents(Guild guild) throws NoTextChannelException {
        GuildRegistration guildRegistration = getGuildRegistration(guild.getIdLong());
        TextChannel textChannel = getTextChannelByIdGuild(guild);
        if (scheduledActivity != null)
            scheduledActivity.shutdown();

        List<Schedule> expiredSchedules = guildRegistration.getSchedules().stream()
            .filter(schedule -> !schedule.getDateTime().isAfter(LocalDateTime.now())).toList();
        if (!expiredSchedules.isEmpty()) {
            guildRegistration.getSchedules().removeAll(expiredSchedules);
            guildRegistrationRepository.saveAndFlush(guildRegistration);
            scheduleRepository.deleteAllById(expiredSchedules.stream().map(BaseEntity::getId).toList());
        }

        LocalDateTime now = LocalDateTime.now();
        List<Schedule> schedules = guildRegistration.getSchedules();

        scheduledActivity = Executors.newScheduledThreadPool(schedules.size());
        for (var schedule : schedules) {
            scheduledActivity.schedule(() -> {
                    textChannel.sendMessage(schedule.getMessage()).queue();
                    guildRegistration.getSchedules().remove(schedule);
                    guildRegistrationRepository.saveAndFlush(guildRegistration);
                    scheduleRepository.deleteById(schedule.getId());
                },
                Duration.between(now, schedule.getDateTime()).getSeconds(),
                TimeUnit.SECONDS
            );
        }
    }
}
