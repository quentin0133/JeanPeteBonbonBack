package fr.dawan.jeanpetebonbon.schedule;

import fr.dawan.jeanpetebonbon.bot.guild.Guild;
import fr.dawan.jeanpetebonbon.core.generic.BaseEntity;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Schedule extends BaseEntity {
    private String message;

    @Builder.Default
    @ElementCollection(fetch = FetchType.EAGER)
    private List<LocalDate> dates = new ArrayList<>();

    @Builder.Default
    @ElementCollection(fetch = FetchType.EAGER)
    private List<LocalTime> times = new ArrayList<>();

    @ManyToOne
    private Guild guild;

    public List<LocalDateTime> getLocalDateTime() {
        return dates.stream()
            .flatMap(date -> times.stream()
                .map(time -> LocalDateTime.of(date, time))).toList();
    }
}
