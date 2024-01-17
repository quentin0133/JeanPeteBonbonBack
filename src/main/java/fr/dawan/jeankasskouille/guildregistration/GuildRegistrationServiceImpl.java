package fr.dawan.jeankasskouille.guildregistration;

import fr.dawan.jeankasskouille.generic.GenericServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GuildRegistrationServiceImpl extends GenericServiceImpl<GuildRegistration, GuildRegistrationRepository, GuildRegistrationDto, GuildRegistrationMapper> implements GuildRegistrationService {
    public GuildRegistrationServiceImpl(GuildRegistrationRepository repository, GuildRegistrationMapper mapper) {
        super(repository, mapper);
    }
}
