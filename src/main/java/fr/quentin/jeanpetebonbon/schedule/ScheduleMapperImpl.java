package fr.quentin.jeanpetebonbon.schedule;

import fr.quentin.jeanpetebonbon.bot.guild.Guild;
import fr.quentin.jeanpetebonbon.schedule.dtos.ScheduleLightDto;
import fr.quentin.jeanpetebonbon.schedule.dtos.ScheduledEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class ScheduleMapperImpl implements ScheduleMapper {

    public ScheduleLightDto toDto(Schedule entity) {
        if (entity == null) {
            return null;
        }

        ScheduleLightDto scheduleLightDto = new ScheduleLightDto();
        scheduleLightDto.setGuildId(this.entityGuildId(entity));
        scheduleLightDto.setId(entity.getId());
        scheduleLightDto.setMessage(entity.getMessage());
        Set<LocalDate> dates = entity.getDates();
        if (dates != null) {
            scheduleLightDto.setDates(new LinkedHashSet<>(dates));
        }

        Set<LocalTime> times = entity.getTimes();
        if (times != null) {
            scheduleLightDto.setTimes(new LinkedHashSet<>(times));
        }

        return scheduleLightDto;
    }

    public Schedule toEntity(ScheduleLightDto dto) {
        if (dto == null) {
            return null;
        }

        Schedule schedule = new Schedule();
        schedule.setId(dto.getId());
        schedule.setVersion(dto.getVersion());
        schedule.setGuild(this.scheduleDtoToGuild(dto));
        schedule.setMessage(dto.getMessage());
        Set<LocalDate> dates = dto.getDates();
        if (dates != null) {
            schedule.setDates(new LinkedHashSet<>(dates));
        }

        Set<LocalTime> times = dto.getTimes();
        if (times != null) {
            schedule.setTimes(new LinkedHashSet<>(times));
        }

        return schedule;
    }

    private Long entityGuildId(Schedule schedule) {
        if (schedule == null) return null;
        Guild guild = schedule.getGuild();
        if (guild == null) return null;
        return guild.getId();
    }

    protected Guild scheduleDtoToGuild(ScheduleLightDto scheduleLightDto) {
        if (scheduleLightDto == null) {
            return null;
        }

        Guild guild = new Guild();
        if (scheduleLightDto.getGuildId() != null) {
            guild.setId(scheduleLightDto.getGuildId());
        }

        return guild;
    }

    @Override
    public List<ScheduledEventDto> toScheduledEvent(Schedule schedule) {
        return schedule.getLocalDateTime().stream()
            .filter(dt -> dt.isAfter(LocalDateTime.now()))
            .map(dt -> new ScheduledEventDto(
                schedule.getId(),
                schedule.getGuild().getId(),
                dt
            ))
            .toList();
    }
}
