package fr.dawan.jeanpetebonbon.message.trigger;

import fr.dawan.jeanpetebonbon.message.response.MessageResponseTrollDto;
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
