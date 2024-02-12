package fr.dawan.jeankasskouille.message.response;

import fr.dawan.jeankasskouille.generic.GenericController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("messages-response-troll")
public class MessageResponseTrollController extends GenericController<MessageResponseTrollDto, MessageResponseTrollService> {
    public MessageResponseTrollController(MessageResponseTrollService service) {
        super(service);
    }
}
