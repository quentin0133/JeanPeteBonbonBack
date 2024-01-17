package fr.dawan.jeankasskouille.guildregistration;

import fr.dawan.jeankasskouille.schedule.Schedule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(fetch = FetchType.EAGER)
    private List<Schedule> schedules;

    public GuildRegistration(long id, long idTextChannel) {
        this.id = id;
        this.idTextChannel = idTextChannel;
        schedules = new ArrayList<>();
    }
}
