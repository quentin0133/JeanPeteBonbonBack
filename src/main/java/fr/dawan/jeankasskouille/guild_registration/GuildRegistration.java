package fr.dawan.jeankasskouille.guild_registration;

import fr.dawan.jeankasskouille.schedule.Schedule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class GuildRegistration {
    @Id
    private long id;
    @Version
    private int version;

    private long idTextChannel;
    private boolean isTrolling;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Schedule> schedules;

    public GuildRegistration(long id) {
        this.id = id;
        idTextChannel = -1;
        schedules = new ArrayList<>();
        isTrolling = true;
    }
}
