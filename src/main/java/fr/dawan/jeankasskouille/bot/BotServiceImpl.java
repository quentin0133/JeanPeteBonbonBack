package fr.dawan.jeankasskouille.bot;

import fr.dawan.jeankasskouille.bot.enums.BotCommand;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class BotServiceImpl extends ListenerAdapter implements BotService {
    private TextChannel textChannel;

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        event.getGuild().updateCommands().addCommands(
            Arrays.stream(BotCommand.values())
                .map(botCommand ->
                    Commands.slash(botCommand.getActionName(), botCommand.getDescription())
                        .addOptions(botCommand.getOptionsData())
                ).toList()
        ).queue();

        LocalDateTime now = LocalDateTime.now();
        long initialDelay = Duration.between(now, now.plusMinutes(1)).getSeconds();

        //initialDelay can become negative if the current day is Thursday but it's already too late
        if (initialDelay >= 0)
        {
            ScheduledExecutorService scheduledActivity = Executors.newScheduledThreadPool(1);
            scheduledActivity.scheduleAtFixedRate(() -> {
                    if (textChannel == null) return;
                    textChannel.sendMessage("Vous avez un évènement ").queue();
                },
                initialDelay, //you have to calculate an initial delay
                TimeUnit.DAYS.toSeconds(7), //makes it run every 7 days
                TimeUnit.SECONDS);
        }
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        ReplyCallbackAction replyCallback = event.reply("Erreur interne, désolé pour la confusion");
        try {
            BotCommand command = BotCommand.valueOf(event.getName().toUpperCase());
            switch (command) {
                case ASSIGN -> {
                    textChannel = event.getChannel().asTextChannel();
                    replyCallback = event.reply("Ce canal a été assigné pour les notifications");
                }
                default -> throw new IllegalArgumentException("Cette commande n'a pas été trouver");
            }
        }
        catch (Exception e) {
            replyCallback = event.reply(e.getMessage()).setEphemeral(true);
            Arrays.stream(e.getStackTrace()).forEach(System.out::println);
        }
        finally {
            replyCallback.queue();
        }
    }
}
