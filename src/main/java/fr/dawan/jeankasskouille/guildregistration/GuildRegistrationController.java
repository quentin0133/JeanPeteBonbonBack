package fr.dawan.jeankasskouille.guildregistration;

import fr.dawan.jeankasskouille.generic.GenericController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("guild-registrations")
public class GuildRegistrationController extends GenericController<GuildRegistrationDto, GuildRegistrationService> {
    public GuildRegistrationController(GuildRegistrationService service) {
        super(service);
    }
}
