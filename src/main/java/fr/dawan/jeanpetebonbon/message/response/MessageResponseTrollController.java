package fr.dawan.jeanpetebonbon.message.response;

import fr.dawan.jeanpetebonbon.core.generic.GenericController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("messages-response-troll")
public class MessageResponseTrollController extends GenericController<MessageResponseTroll, MessageResponseTrollDto, MessageResponseTrollService> {
    public MessageResponseTrollController(MessageResponseTrollService service) {
        super(service);
    }
}
