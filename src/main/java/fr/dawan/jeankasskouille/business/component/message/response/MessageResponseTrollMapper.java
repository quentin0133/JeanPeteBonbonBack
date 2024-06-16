package fr.dawan.jeankasskouille.business.component.message.response;

import fr.dawan.jeankasskouille.generic.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MessageResponseTrollMapper extends GenericMapper<MessageResponseTroll, MessageResponseTrollDto> {
}
