package fr.dawan.jeankasskouille.message.reaction;

import fr.dawan.jeankasskouille.generic.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MessageReactionTrollMapper extends GenericMapper<MessageReactionTroll, MessageReactionTrollDto> {
}
