package fr.dawan.jeanpetebonbon.message.reaction;

import fr.dawan.jeanpetebonbon.core.generic.GenericController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("messages-reaction-troll")
public class MessageReactionTrollController extends GenericController<MessageReactionTroll, MessageReactionTrollDto, MessageReactionTrollService> {
    public MessageReactionTrollController(MessageReactionTrollService service) {
        super(service);
    }
}
