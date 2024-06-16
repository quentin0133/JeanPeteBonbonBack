package fr.dawan.jeankasskouille.business.component.bot;

import fr.dawan.jeankasskouille.business.component.bot.enums.BotCommand;
import fr.dawan.jeankasskouille.business.component.bot.guild.Guild;
import fr.dawan.jeankasskouille.business.component.bot.guild.GuildRepository;
import fr.dawan.jeankasskouille.business.component.message.clash.MessageClashTrollRepository;
import fr.dawan.jeankasskouille.business.component.message.reaction.MessageReactionTroll;
import fr.dawan.jeankasskouille.business.component.message.reaction.MessageReactionTrollRepository;
import fr.dawan.jeankasskouille.business.component.message.trigger.MessageTriggerTroll;
import fr.dawan.jeankasskouille.business.component.message.trigger.MessageTriggerTrollRepository;
import fr.dawan.jeankasskouille.business.component.schedule.Schedule;
import fr.dawan.jeankasskouille.business.component.schedule.ScheduleRepository;
import fr.dawan.jeankasskouille.exception.DateTimeBeforeException;
import fr.dawan.jeankasskouille.exception.NoServerFound;
import fr.dawan.jeankasskouille.exception.NoTextChannelException;
import fr.dawan.jeankasskouille.tools.RandomUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ShutdownEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static fr.dawan.jeankasskouille.tools.DateUtils.getMostLateTime;
import static fr.dawan.jeankasskouille.tools.DateUtils.getMostRecentDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class BotServiceImpl extends ListenerAdapter implements BotService {
    public static final float PERCENTAGE_TROLL_REACTION = 0.3f;
    public static final float PERCENTAGE_TROLL_MESSAGE = 0.5f;
    public static final int COOLDOWN_TROLL_SECONDS = 30;

    private final ScheduleRepository scheduleRepository;
    private final GuildRepository guildRepository;
    private final MessageTriggerTrollRepository messageTriggerTrollRepository;
    private final MessageReactionTrollRepository messageReactionTrollRepository;
    private final MessageClashTrollRepository messageClashTrollRepository;

    private final Map<Long, Map.Entry<ScheduledExecutorService, Delayed>> scheduledCooldown = new HashMap<>();

    private final Map<Long, ScheduledExecutorService> scheduledEvent = new HashMap<>();
    private final Map<Long, ScheduledFuture<?>> schedulesEventHandler = new HashMap<>();

    //region JDA events
    @Override
    public void onGuildReady(GuildReadyEvent event) {
        event.getGuild().updateCommands().addCommands(
            Arrays.stream(BotCommand.values())
                .map(botCommand ->
                    Commands.slash(botCommand.getActionName(), botCommand.getDescription())
                        .addOptions(botCommand.getOptionsData())
                ).toList()
        ).queue();

        scheduledEvent.put(event.getGuild().getIdLong(), Executors.newScheduledThreadPool(1));
        schedulesEventHandler.put(event.getGuild().getIdLong(), null);

        try {
            initEvents(event.getGuild());
        }
        catch (NoServerFound e) {
            guildRepository.saveAndFlush(new Guild(event.getGuild().getIdLong(), event.getGuild().getName()));
        }
        catch (NoTextChannelException ignored) {}
    }

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        Guild guild = getGuildRegistration(event.getGuild().getIdLong());
        clearGuildRegistration(guild);
    }

    @Override
    public void onShutdown(@NotNull ShutdownEvent event) {
        guildRepository.findAll().forEach(this::clearGuildRegistration);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        ReplyCallbackAction replyCallback = event.reply("Erreur interne, désolé pour la confusion");
        net.dv8tion.jda.api.entities.Guild guild = event.getGuild();
        long idGuild = Objects.requireNonNull(guild).getIdLong();
        try {
            BotCommand command = BotCommand.valueOf(event.getName().toUpperCase());
            switch (command) {
                case ASSIGN -> {
                    Guild guildRegistration = getGuildRegistration(idGuild);
                    guildRegistration.setIdTextChannel(event.getChannelIdLong());
                    guildRepository.saveAndFlush(guildRegistration);
                    replyCallback = event.reply("Ce canal a été assigné pour les notifications");
                }
                case SET_DATE_HOUR, SET_HOUR -> {
                    String message = Objects.requireNonNull(event.getOption("message")).getAsString();
                    String date = command == BotCommand.SET_DATE_HOUR ?
                            Objects.requireNonNull(event.getOption("date")).getAsString() :
                            LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    String time = Objects.requireNonNull(event.getOption("time")).getAsString();

                    replyCallback = setEvent(message, date, time, event);
                }
                default -> throw new IllegalArgumentException("Cette commande n'a pas encore été implémenter");
            }
        }
        catch (DateTimeBeforeException dtbe) {
            replyCallback = event.reply("La date doit être après la date courante").setEphemeral(true);
        }
        catch (DateTimeParseException dte) {
            replyCallback = event.reply("La date ne respecte pas le format demandé").setEphemeral(true);
        }
        catch (Exception e) {
            replyCallback = event.reply(e.getMessage()).setEphemeral(true);
            Arrays.stream(e.getStackTrace()).forEach(stackTraceElement -> log.error(stackTraceElement.toString()));
        } finally {
            replyCallback.queue();
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        SelfUser bot = event.getJDA().getSelfUser();

        // If it's our bot that send a message, don't bother to check if we troll
        // or not, the bot is not dumb
        if (event.getAuthor().equals(bot)) return;

        // Will clash any scum who dare to answer or mention the bot
        // Note : not affected by the cooldown, the bot always reply
        if (checkMessageClash(bot, message)) {
            messageClash(event);
            return;
        }

        // Check the cooldown of the trolling message bot
        if (scheduledCooldown.containsKey(event.getGuild().getIdLong())) {
            event.getChannel().sendMessage("En cooldown : %d".formatted(scheduledCooldown
                    .get(event.getGuild().getIdLong()).getValue().getDelay(TimeUnit.SECONDS))).queue();
            return;
        }

        messageTroll(event);
    }
    //endregion

    //region private method
    /**
     * Check if the bot need to clash the user if he mentions him or reply one of his message
     * @param bot the reference of our bot
     * @param message message of the user
     * @return do the bot need to clash the user
     */
    private boolean checkMessageClash(SelfUser bot, Message message) {
        boolean isMessageReference = message.getMessageReference() != null;
        boolean isMentionned = message.getMentions().isMentioned(bot);
        if (isMessageReference) {
            boolean isMessageReferenceHasMessage = message.getMessageReference().getMessage() != null;
            return isMessageReferenceHasMessage && message.getMentions().isMentioned(bot) ||
                    message.getMessageReference().getMessage().getAuthor().equals(bot);
        }
        return isMentionned;
    }

    private void messageClash(MessageReceivedEvent event) {
        Message message = event.getMessage();
        message.reply(RandomUtils.getRandomList(messageClashTrollRepository.findAll()).getMessageClash()
        ).queue();
    }

    private void messageTroll(MessageReceivedEvent event) {
        long idGuild = event.getGuild().getIdLong();
        List<String> replies = new ArrayList<>();
        Message message = event.getMessage();
        replies.add(triggerMessageTroll(message));
        replies.add(reactionTroll(message));
        String result = replies.stream().filter(reply -> reply != null && !reply.isEmpty())
                .collect(Collectors.joining(" + "));

        if (!result.isEmpty()) {
            message.reply(result).queue();
            ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
            scheduledCooldown.put(idGuild, Map.entry(scheduledExecutorService,
                    scheduledExecutorService.schedule(() -> scheduledCooldown.remove(idGuild),
                Duration.between(LocalDateTime.now(), LocalDateTime.now().plusSeconds(COOLDOWN_TROLL_SECONDS))
                        .getSeconds(),
                TimeUnit.SECONDS
            )));
        }
    }

    private ReplyCallbackAction setEvent(String message, String date, String time,
                                         SlashCommandInteractionEvent event)
        throws DateTimeBeforeException, NoTextChannelException, NoServerFound {
        net.dv8tion.jda.api.entities.Guild guild = event.getGuild();
        if (guild == null) throw new NoServerFound();
        Guild guildRegistration = getGuildRegistration(guild.getIdLong());

        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        if (localDate.isBefore(LocalDate.now()))
            throw new DateTimeBeforeException();

        LocalTime localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm:ss"));
        if (localTime.isBefore(LocalTime.now()))
            throw new DateTimeBeforeException();
        Schedule schedule = scheduleRepository.findByMessage(message)
                .orElseGet(() -> Schedule.builder().message(message).guild(guildRegistration).build());
        schedule.getDates().add(localDate);
        schedule.getTimes().add(localTime);
        scheduleRepository.saveAndFlush(schedule);

        startSchedule(guild, schedule, getTextChannelByIdGuild(guild));

        return event.reply("L'évènement enregistré avec succès, il sera exécuté le %s à %s"
                .formatted(localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
    }

    private TextChannel getTextChannelByIdGuild(net.dv8tion.jda.api.entities.Guild guild)
            throws NoTextChannelException, NoServerFound {
        Guild guildRegistration = getGuildRegistration(guild.getIdLong());
        if (guildRegistration.getIdTextChannel() == -1) throw new NoTextChannelException();
        return guild.getChannelById(TextChannel.class, guildRegistration.getIdTextChannel());
    }

    private Guild getGuildRegistration(long idGuild) throws NoServerFound {
        Optional<Guild> guildOptional = guildRepository.findById(idGuild);
        if (guildOptional.isEmpty())
            throw new NoServerFound();
        return guildOptional.get();
    }

    private void clearGuildRegistration(Guild guild) {
        scheduledEvent.get(guild.getId()).shutdown();
        scheduledCooldown.get(guild.getId()).getKey().shutdown();
        guildRepository.deleteById(guild.getId());
        ScheduledFuture<?> scheduledHandler = schedulesEventHandler.get(guild.getId());
        if (scheduledHandler != null)
            scheduledHandler.cancel(true);
    }

    private void initEvents(net.dv8tion.jda.api.entities.Guild guild) throws NoTextChannelException {
        TextChannel textChannel = getTextChannelByIdGuild(guild);
        List<Schedule> schedules = scheduleRepository.findByGuildId(guild.getIdLong());
        getLastSchedule(schedules.stream().filter(this::removeAncientSchedule).toList())
                .ifPresent(schedule -> startSchedule(guild, schedule, textChannel));
    }

    private boolean removeAncientSchedule(Schedule schedule) {
        // Delete when schedules doesn't have more schedules date valid
        // Update the schedules to remove the date that are in the past
        LocalTime mostLateTime = getMostLateTime(schedule.getTimes()).orElse(LocalTime.MAX);
        System.err.println(mostLateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        schedule.getDates().removeAll(
            schedule.getDates().stream().filter(date ->
                    date.isAfter(LocalDate.now()) || date.equals(LocalDate.now()) &&
                        (mostLateTime.isBefore(LocalTime.now()) || mostLateTime.equals(LocalTime.now())))
                .toList()
        );
        if (schedule.getDates().isEmpty()) {
            scheduleRepository.deleteById(schedule.getId());
            return false;
        }
        return true;
    }

    private Optional<Schedule> getLastSchedule(List<Schedule> schedules) {
        if (schedules.isEmpty()) return Optional.empty();
        return schedules.stream().min(Comparator.comparing(other ->
                    getMostRecentDate(other.getLocalDateTime()).orElse(LocalDateTime.MAX)));
    }

    private void startSchedule(net.dv8tion.jda.api.entities.Guild guild, Schedule schedule,
                               TextChannel textChannel) {
        ScheduledFuture<?> scheduledFuture = schedulesEventHandler.get(guild.getIdLong());
        if (scheduledFuture != null)
            scheduledFuture.cancel(true);
        LocalDateTime localDateTimeNext = getMostRecentDate(schedule.getLocalDateTime().stream()
                .filter(localDateTime -> localDateTime.isAfter(LocalDateTime.now())).toList())
                .orElseThrow(IllegalArgumentException::new);
        schedulesEventHandler.put(guild.getIdLong(),
            scheduledEvent.get(guild.getIdLong()).schedule(() -> {
                    sendScheduleMessage(schedule, textChannel);
                        scheduleRepository.findByGuildId(guild.getIdLong()).stream()
                        .filter(otherShedule -> otherShedule.getLocalDateTime().contains(localDateTimeNext))
                        .forEach(sameSchedule -> sendScheduleMessage(sameSchedule, textChannel));

                    schedulesEventHandler.put(guild.getIdLong(), null);

                    getLastSchedule(scheduleRepository.findByGuildId(guild.getIdLong()))
                            .ifPresent(nextSchedule -> startSchedule(guild, nextSchedule, textChannel));
                },
                Duration.between(LocalDateTime.now(), localDateTimeNext).getSeconds(),
                TimeUnit.SECONDS
            )
        );
    }

    private void sendScheduleMessage(Schedule schedule, TextChannel textChannel) {
        textChannel.sendMessage(schedule.getMessage()).queue();
        removeAncientSchedule(schedule);
    }

    private String reactionTroll(Message message) {
        if (!RandomUtils.getRandomByPercentage(PERCENTAGE_TROLL_REACTION))
            return null;

        MessageReactionTroll trollReaction = RandomUtils.getRandomList(messageReactionTrollRepository
                .findAll());

        if (trollReaction != null) {
            message.addReaction(Emoji.fromUnicode(trollReaction.getUnicodeEmoji())).queue();
            if (trollReaction.getMessageResponse() != null)
                return trollReaction.getMessageResponse();
        }

        return null;
    }

    private String triggerMessageTroll(Message message) {
        // Filter all the noise the user can add to dodge the trolling message (special character / space)
        String[] content = Normalizer.normalize(message.getContentDisplay().trim(), Normalizer.Form.NFKD)
                .replaceAll("[^\\p{L}\\d\\s_]", "").split(" ");

        // We check only the last word
        String lastWord = content[content.length - 1];

        // We retrieve the message from the database with the condition to have something similar
        Optional<MessageTriggerTroll> messageTriggerTrollOptional = messageTriggerTrollRepository
                .findByMessageTrigger(lastWord);

        if (messageTriggerTrollOptional.isPresent() && RandomUtils
                .getRandomByPercentage(PERCENTAGE_TROLL_MESSAGE))
            return "-%s".formatted(RandomUtils.getRandomList(messageTriggerTrollOptional.get()
                    .getMessagesResponseTroll()).getMessageResponse());
        return null;
    }
    //endregion
}
