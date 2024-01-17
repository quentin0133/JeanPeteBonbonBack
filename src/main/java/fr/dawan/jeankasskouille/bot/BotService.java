package fr.dawan.jeankasskouille.bot;

import fr.dawan.jeankasskouille.bot.exceptions.NoTextChannelException;
import fr.dawan.jeankasskouille.guildregistration.GuildRegistration;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public interface BotService {
    TextChannel getTextChannelByIdGuild(net.dv8tion.jda.api.entities.Guild guild) throws NoTextChannelException;
    GuildRegistration getGuildRegistration(long idGuild) throws NoTextChannelException;
    void updateEvents(Guild guild) throws NoTextChannelException;
}
