package fr.dawan.jeankasskouille.message.trigger;

import fr.dawan.jeankasskouille.generic.GenericController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("messages-trigger-troll")
public class MessageTriggerTrollController extends GenericController<MessageTriggerTrollDto, MessageTriggerTrollService> {
    public MessageTriggerTrollController(MessageTriggerTrollService service) {
        super(service);
    }
}
