package fr.quentin.jeanpetebonbon.schedule;

import fr.quentin.jeanpetebonbon.core.generic.GenericMapper;
import fr.quentin.jeanpetebonbon.schedule.dtos.ScheduleLightDto;
import fr.quentin.jeanpetebonbon.schedule.dtos.ScheduledEventDto;

import java.util.List;

public interface ScheduleMapper extends GenericMapper<Schedule, ScheduleLightDto> {
    List<ScheduledEventDto> toScheduledEvent(Schedule schedule);
}
