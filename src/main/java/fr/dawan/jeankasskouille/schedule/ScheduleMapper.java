package fr.dawan.jeankasskouille.schedule;

import fr.dawan.jeankasskouille.generic.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ScheduleMapper extends GenericMapper<Schedule,ScheduleDto> {
}
