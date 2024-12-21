package fr.dawan.jeanpetebonbon.bot.guild;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Guild {
    @Id
    private long id;

    @Version
    private int version;

    private String label;

    private long idTextChannel;

    private boolean trolling;

    public Guild(long id, String label) {
        this.id = id;
        this.label = label;
        idTextChannel = -1;
        trolling = true;
    }
}
