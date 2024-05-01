package fr.dawan.jeankasskouille.bot;

import fr.dawan.jeankasskouille.bot.enums.BotCommand;
import fr.dawan.jeankasskouille.bot.exceptions.DateTimeBeforeException;
import fr.dawan.jeankasskouille.bot.exceptions.NoServerFound;
import fr.dawan.jeankasskouille.bot.exceptions.NoTextChannelException;
import fr.dawan.jeankasskouille.generic.BaseEntity;
import fr.dawan.jeankasskouille.guild_registration.GuildRegistration;
import fr.dawan.jeankasskouille.guild_registration.GuildRegistrationRepository;
import fr.dawan.jeankasskouille.message.clash.MessageClashTrollRepository;
import fr.dawan.jeankasskouille.message.reaction.MessageReactionTroll;
import fr.dawan.jeankasskouille.message.reaction.MessageReactionTrollRepository;
import fr.dawan.jeankasskouille.message.trigger.MessageTriggerTroll;
import fr.dawan.jeankasskouille.message.trigger.MessageTriggerTrollRepository;
import fr.dawan.jeankasskouille.schedule.Schedule;
import fr.dawan.jeankasskouille.schedule.ScheduleRepository;
import fr.dawan.jeankasskouille.tools.RandomTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Guild;
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
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BotServiceImpl extends ListenerAdapter implements BotService {
    public static final float PERCENTAGE_TROLL_REACTION = 0.3f;
    public static final float PERCENTAGE_TROLL_MESSAGE = 0.5f;
    public static final int COOLDOWN_TROLL_SECONDS = 30;

    private final ScheduleRepository scheduleRepository;
    private final GuildRegistrationRepository guildRegistrationRepository;
    private final MessageTriggerTrollRepository messageTriggerTrollRepository;
    private final MessageReactionTrollRepository messageReactionTrollRepository;
    private final MessageClashTrollRepository messageClashTrollRepository;

    private final Map<Long, Map.Entry<ScheduledExecutorService, Delayed>> scheduledCooldown = new HashMap<>();

    private ScheduledExecutorService scheduledEvent;

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

        try {
            updateEvents(event.getGuild());
        }
        catch (NoServerFound e) {
            guildRegistrationRepository.saveAndFlush(new GuildRegistration(event.getGuild().getIdLong()));
        }
        catch (NoTextChannelException ignored) {}
    }

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        GuildRegistration guildRegistration = getGuildRegistration(event.getGuild().getIdLong());
        clearGuildRegistration(guildRegistration);
    }

    @Override
    public void onShutdown(ShutdownEvent event) {
        guildRegistrationRepository.findAll().forEach(this::clearGuildRegistration);
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
                    GuildRegistration guildRegistration = getGuildRegistration(idGuild);
                    guildRegistration.setIdTextChannel(event.getChannelIdLong());
                    guildRegistrationRepository.saveAndFlush(guildRegistration);
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

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        SelfUser bot = event.getJDA().getSelfUser();

        // If it's our bot that send a message, don't bother to check if we troll
        // or not, the bot is not dumb
        if (event.getAuthor().equals(bot)) return;

        // Will clash any scum who dare to answer or mention the bot
        // Note : not affected by the cooldown, the bot always replies
        if (checkMessageClash(bot, message)) {
            messageClash(event);
            return;
        }

        // Check the cooldown of the trolling message bot
        if (scheduledCooldown.containsKey(event.getGuild().getIdLong())) {
            event.getChannel().sendMessage("En cooldown : %d".formatted(scheduledCooldown.get(event.getGuild().getIdLong()).getValue().getDelay(TimeUnit.SECONDS))).queue();
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
        if (isMessageReference) {
            boolean isMessageReferenceHasMessage = message.getMessageReference().getMessage() != null;
            return isMessageReferenceHasMessage && message.getMentions().isMentioned(bot) ||
                    message.getMessageReference().getMessage().getAuthor().equals(bot);
        }
        return false;
    }

    private void messageClash(MessageReceivedEvent event) {
        Message message = event.getMessage();
        message.reply(RandomTool.getRandomList(messageClashTrollRepository.findAll()).getMessageClash()
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
                Duration.between(LocalDateTime.now(), LocalDateTime.now().plusSeconds(COOLDOWN_TROLL_SECONDS)).getSeconds(),
                TimeUnit.SECONDS
            )));
        }
    }

    private ReplyCallbackAction setEvent(String message, String date, String time, SlashCommandInteractionEvent event)
        throws DateTimeBeforeException, NoTextChannelException, NoServerFound {
        Guild guild = event.getGuild();
        if (guild == null) throw new NoServerFound();
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

    private TextChannel getTextChannelByIdGuild(Guild guild) throws NoTextChannelException, NoServerFound {
        GuildRegistration guildRegistration = getGuildRegistration(guild.getIdLong());
        if (guildRegistration.getIdTextChannel() == -1) throw new NoTextChannelException();
        return guild.getChannelById(TextChannel.class, guildRegistration.getIdTextChannel());
    }

    private GuildRegistration getGuildRegistration(long idGuild) throws NoServerFound {
        Optional<GuildRegistration> guildOptional = guildRegistrationRepository.findById(idGuild);
        if (guildOptional.isEmpty())
            throw new NoServerFound();
        return guildOptional.get();
    }

    private void clearGuildRegistration(GuildRegistration guildRegistration) {
        scheduledEvent.shutdown();
        scheduledCooldown.get(guildRegistration.getId()).getKey().shutdown();
        guildRegistrationRepository.deleteById(guildRegistration.getId());
    }

    private void updateEvents(Guild guild) throws NoTextChannelException {
        GuildRegistration guildRegistration = getGuildRegistration(guild.getIdLong());
        TextChannel textChannel = getTextChannelByIdGuild(guild);
        LocalDateTime now = LocalDateTime.now();
        List<Schedule> schedules = guildRegistration.getSchedules();

        if (scheduledEvent != null)
            scheduledEvent.shutdown();

        List<Schedule> expiredSchedules = guildRegistration.getSchedules().stream()
            .filter(schedule -> !schedule.getDateTime().isAfter(LocalDateTime.now())).toList();

        if (!expiredSchedules.isEmpty()) {
            guildRegistration.getSchedules().removeAll(expiredSchedules);
            guildRegistrationRepository.saveAndFlush(guildRegistration);
            scheduleRepository.deleteAllById(expiredSchedules.stream().map(BaseEntity::getId).toList());
        }

        scheduledEvent = Executors.newScheduledThreadPool(schedules.size());
        for (var schedule : schedules) {
            scheduledEvent.schedule(() -> {
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

    private String reactionTroll(Message message) {
        if (!RandomTool.getRandomByPercentage(PERCENTAGE_TROLL_REACTION))
            return null;

        MessageReactionTroll trollReaction = RandomTool.getRandomList(messageReactionTrollRepository.findAll());

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
        Optional<MessageTriggerTroll> messageTriggerTrollOptional = messageTriggerTrollRepository.findByMessageTrigger(lastWord);

        if (messageTriggerTrollOptional.isPresent() && RandomTool.getRandomByPercentage(PERCENTAGE_TROLL_MESSAGE))
            return "-%s".formatted(RandomTool.getRandomList(messageTriggerTrollOptional.get().getMessagesResponseTroll()).getMessageResponse());
        return null;
    }
    //endregion
}
