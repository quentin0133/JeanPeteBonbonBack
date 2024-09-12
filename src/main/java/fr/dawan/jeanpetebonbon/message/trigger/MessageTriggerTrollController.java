package fr.dawan.jeanpetebonbon.message.trigger;

import fr.dawan.jeanpetebonbon.core.generic.GenericController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("messages-trigger-troll")
public class MessageTriggerTrollController extends GenericController<MessageTriggerTroll, MessageTriggerTrollDto, MessageTriggerTrollService> {
    public MessageTriggerTrollController(MessageTriggerTrollService service) {
        super(service);
    }
}
