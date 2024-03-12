package fr.dawan.jeankasskouille.guild_registration;

import fr.dawan.jeankasskouille.generic.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GuildRegistrationMapper extends GenericMapper<GuildRegistration, GuildRegistrationDto> {
    @Override
    @Mapping(target = "trolling", ignore = true)
    @Mapping(target = "schedules", ignore = true)
    GuildRegistration toEntity(GuildRegistrationDto dto);
}
