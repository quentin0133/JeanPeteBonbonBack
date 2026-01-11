package fr.quentin.jeanpetebonbon.bot.guild;

import fr.quentin.jeanpetebonbon.bot.guild.dtos.GuildDto;
import fr.quentin.jeanpetebonbon.core.generic.CrudServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GuildServiceImpl extends CrudServiceImpl<Guild, GuildRepository, GuildDto, GuildMapper> implements GuildService {
    public GuildServiceImpl(GuildRepository repository, GuildMapper mapper) {
        super(repository, mapper, Guild.class);
    }

    @Override
    public boolean existsById(long id) {
        return repository.existsById(id);
    }
}
