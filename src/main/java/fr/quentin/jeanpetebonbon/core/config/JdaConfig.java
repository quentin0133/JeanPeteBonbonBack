package fr.quentin.jeanpetebonbon.core.config;

import fr.quentin.jeanpetebonbon.bot.BotService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JdaConfig {
    private static final String ACTIVITY_STATUS = "Je te péterai les bonbons quand tu t'y attendra pas";

    @Value("${jda.token}")
    private String token;

    @Bean(destroyMethod = "shutdown")
    public JDA jda(BotService botService) throws InterruptedException {
        return JDABuilder.createDefault(token)
            .setMemberCachePolicy(MemberCachePolicy.ALL)
            .enableIntents(
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_MESSAGE_REACTIONS,
                GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.MESSAGE_CONTENT
            )
            .setChunkingFilter(ChunkingFilter.ALL)
            .setBulkDeleteSplittingEnabled(false)
            .setActivity(Activity.customStatus(ACTIVITY_STATUS))
            .addEventListeners(botService)
            .build()
            .awaitReady();
    }
}
