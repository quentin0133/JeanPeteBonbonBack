package fr.quentin.jeanpetebonbon.schedule.dtos;

import fr.quentin.jeanpetebonbon.core.generic.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleLightDto extends BaseDto {
    private String message;
    private Long guildId;
    private Set<LocalDate> dates;
    private Set<LocalTime> times;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        ScheduleLightDto that = (ScheduleLightDto) object;
        return Objects.equals(message, that.message) && Objects.equals(guildId, that.guildId) && Objects.equals(dates, that.dates) && Objects.equals(times, that.times);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), message, guildId, dates, times);
    }

    @Override
    public String toString() {
        return "ScheduleDto{" +
            "message='" + message + '\'' +
            ", guildId=" + guildId +
            ", dates=" + dates +
            ", times=" + times +
            '}';
    }
}
