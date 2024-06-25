package fr.dawan.jeankasskouille.message.trigger;

import fr.dawan.jeankasskouille.message.response.MessageResponseTrollDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageTriggerTrollDto {
    private long id;
    private int version;
    private String messageTrigger;
    private List<MessageResponseTrollDto> messagesResponseTroll;
}
