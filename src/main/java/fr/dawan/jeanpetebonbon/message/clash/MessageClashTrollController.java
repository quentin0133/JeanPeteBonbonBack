package fr.dawan.jeanpetebonbon.message.clash;

import fr.dawan.jeanpetebonbon.core.generic.GenericController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("messages-clash-troll")
public class MessageClashTrollController extends GenericController<MessageClashTrollDto, MessageClashTrollService> {
    public MessageClashTrollController(MessageClashTrollService service) {
        super(service);
    }
}
