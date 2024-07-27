package fr.dawan.jeanpetebonbon.message.trigger;

import fr.dawan.jeanpetebonbon.core.generic.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MessageTriggerTrollMapper extends GenericMapper<MessageTriggerTroll, MessageTriggerTrollDto> {
}
