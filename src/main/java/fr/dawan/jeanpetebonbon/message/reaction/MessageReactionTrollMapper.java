package fr.dawan.jeanpetebonbon.message.reaction;

import fr.dawan.jeanpetebonbon.core.generic.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MessageReactionTrollMapper extends GenericMapper<MessageReactionTroll, MessageReactionTrollDto> {
}
