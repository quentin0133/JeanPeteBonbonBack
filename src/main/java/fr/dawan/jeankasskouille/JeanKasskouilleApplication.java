package fr.dawan.jeankasskouille;

import fr.dawan.jeankasskouille.bot.BotService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class JeanKasskouilleApplication {
	public static JDA jda;

	public static void main(String[] args) {
		SpringApplication.run(JeanKasskouilleApplication.class, args);
	}

	@Autowired
	private BotService botService;

	@EventListener(ApplicationStartedEvent.class)
	void StartDiscord() {
		jda = JDABuilder.createDefault(token)
			.setMemberCachePolicy(MemberCachePolicy.ALL)
			.enableIntents(GatewayIntent.GUILD_MEMBERS,
				GatewayIntent.GUILD_MESSAGE_REACTIONS,
				GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
				GatewayIntent.GUILD_MESSAGES,
				GatewayIntent.MESSAGE_CONTENT
			)
			.setChunkingFilter(ChunkingFilter.ALL)
			.setBulkDeleteSplittingEnabled(false)
			.setActivity(Activity.watching("les gens qu'il peut ennuyer"))
			.addEventListeners(botService)
			.build();
	}
}
