package fr.dawan.jeankasskouille.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDto {
    private long id;
    private int version;
    private LocalDateTime dateTime;
}
