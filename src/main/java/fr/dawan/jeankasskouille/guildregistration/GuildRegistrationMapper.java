package fr.dawan.jeankasskouille.guildregistration;

import fr.dawan.jeankasskouille.generic.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GuildRegistrationMapper extends GenericMapper<GuildRegistration, GuildRegistrationDto> {
}
