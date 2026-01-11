package fr.quentin.jeanpetebonbon.schedule;

import fr.quentin.jeanpetebonbon.bot.guild.Guild;
import fr.quentin.jeanpetebonbon.core.generic.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

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
public class Schedule extends BaseEntity {
    private String message;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<LocalDate> dates = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<LocalTime> times = new HashSet<>();

    @ManyToOne
    private Guild guild;

    public Schedule(String message, Guild guild) {
        this.message = message;
        this.guild = guild;
    }

    public Set<LocalDateTime> getLocalDateTime() {
        return dates.stream()
            .flatMap(date -> times.stream()
                .map(time -> LocalDateTime.of(date, time))).collect(Collectors.toSet());
    }
}
