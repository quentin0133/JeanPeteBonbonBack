package fr.dawan.jeankasskouille.message.reaction;

import fr.dawan.jeankasskouille.generic.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class MessageReactionTroll extends BaseEntity {
    private final String unicodeEmoji;
    private String description;

    public MessageReactionTroll() {
        unicodeEmoji = "";
    }
}
