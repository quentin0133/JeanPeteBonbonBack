package fr.dawan.jeanpetebonbon.schedule;

import fr.dawan.jeanpetebonbon.bot.guild.Guild;
import fr.dawan.jeanpetebonbon.core.generic.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
    private Set<LocalDate> dates = new HashSet<>();

    @Builder.Default
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<LocalTime> times = new HashSet<>();

    @ManyToOne
    private Guild guild;

    public Set<LocalDateTime> getLocalDateTime() {
        return dates.stream()
            .flatMap(date -> times.stream()
                .map(time -> LocalDateTime.of(date, time))).collect(Collectors.toSet());
    }
}
