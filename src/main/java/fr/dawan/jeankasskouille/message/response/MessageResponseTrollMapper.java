package fr.dawan.jeankasskouille.message.response;

import fr.dawan.jeankasskouille.core.generic.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MessageResponseTrollMapper extends GenericMapper<MessageResponseTroll, MessageResponseTrollDto> {
}
