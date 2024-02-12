package fr.dawan.jeankasskouille.message.trigger;

import fr.dawan.jeankasskouille.generic.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MessageTriggerTrollMapper extends GenericMapper<MessageTriggerTroll, MessageTriggerTrollDto> {
}
