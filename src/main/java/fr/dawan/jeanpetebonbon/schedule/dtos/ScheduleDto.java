package fr.dawan.jeanpetebonbon.schedule.dtos;

import fr.dawan.jeanpetebonbon.core.generic.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ScheduleDto extends BaseDto {
    private String message;
    private Long guildId;
    private List<LocalDate> dates;
    private List<LocalTime> times;
}
