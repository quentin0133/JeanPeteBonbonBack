package fr.dawan.jeankasskouille.business.component.message.clash;

import fr.dawan.jeankasskouille.generic.GenericController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("messages-clash-troll")
public class MessageClashTrollController extends GenericController<MessageClashTrollDto, MessageClashTrollService> {
    public MessageClashTrollController(MessageClashTrollService service) {
        super(service);
    }
}
