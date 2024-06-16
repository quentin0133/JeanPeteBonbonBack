package fr.dawan.jeankasskouille.business.component.bot.guild;

import fr.dawan.jeankasskouille.business.component.bot.guild.dtos.GuildDto;
import fr.dawan.jeankasskouille.generic.GenericController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("guild-registrations")
public class GuildController extends GenericController<GuildDto, GuildService> {
    public GuildController(GuildService service) {
        super(service);
    }
}
