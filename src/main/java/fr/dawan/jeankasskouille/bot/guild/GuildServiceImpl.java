package fr.dawan.jeankasskouille.bot.guild;

import fr.dawan.jeankasskouille.bot.guild.dtos.GuildDto;
import fr.dawan.jeankasskouille.core.generic.GenericServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GuildServiceImpl extends GenericServiceImpl<Guild, GuildRepository, GuildDto, GuildMapper> implements GuildService {
    public GuildServiceImpl(GuildRepository repository, GuildMapper mapper) {
        super(repository, mapper);
    }
}
