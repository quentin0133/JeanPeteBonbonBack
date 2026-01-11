package fr.quentin.jeanpetebonbon.message.trigger.dtos;

import fr.quentin.jeanpetebonbon.message.response.dtos.MessageResponseTrollDto;
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
