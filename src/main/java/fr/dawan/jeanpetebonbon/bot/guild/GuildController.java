package fr.dawan.jeanpetebonbon.bot.guild;

import fr.dawan.jeanpetebonbon.bot.guild.dtos.GuildDto;
import fr.dawan.jeanpetebonbon.core.generic.GenericController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/guilds")
public class GuildController extends GenericController<Guild, GuildDto, GuildService> {
    public GuildController(GuildService service) {
        super(service);
    }
}
