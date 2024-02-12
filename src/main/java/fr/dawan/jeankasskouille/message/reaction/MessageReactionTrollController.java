package fr.dawan.jeankasskouille.message.reaction;

import fr.dawan.jeankasskouille.generic.GenericController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("messages-reaction-troll")
public class MessageReactionTrollController extends GenericController<MessageReactionTrollDto, MessageReactionTrollService> {
    public MessageReactionTrollController(MessageReactionTrollService service) {
        super(service);
    }
}
