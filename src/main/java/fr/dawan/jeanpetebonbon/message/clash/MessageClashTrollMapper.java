package fr.dawan.jeanpetebonbon.message.clash;

import fr.dawan.jeanpetebonbon.core.generic.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MessageClashTrollMapper extends GenericMapper<MessageClashTroll, MessageClashTrollDto> {
}
