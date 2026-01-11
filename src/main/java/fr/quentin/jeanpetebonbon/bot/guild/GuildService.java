package fr.quentin.jeanpetebonbon.bot.guild;

import fr.quentin.jeanpetebonbon.bot.guild.dtos.GuildDto;
import fr.quentin.jeanpetebonbon.core.generic.GenericService;

public interface GuildService extends GenericService<Guild, GuildDto> {
  boolean existsById(long idLong);
}
