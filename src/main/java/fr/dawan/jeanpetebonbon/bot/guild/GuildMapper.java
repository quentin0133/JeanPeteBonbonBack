package fr.dawan.jeanpetebonbon.bot.guild;

import fr.dawan.jeanpetebonbon.bot.guild.dtos.GuildDto;
import fr.dawan.jeanpetebonbon.core.generic.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface GuildMapper extends GenericMapper<Guild, GuildDto> {
    @Override
    GuildDto toDto(Guild entity);
}