package fr.dawan.jeankasskouille.business.component.message.reaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageReactionTrollDto {
    private long id;
    private int version;
    private String messageResponse;
}
