package fr.dawan.jeankasskouille.business.component.message.response;

import fr.dawan.jeankasskouille.generic.GenericServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageResponseTrollServiceImpl extends GenericServiceImpl<MessageResponseTroll, MessageResponseTrollRepository, MessageResponseTrollDto, MessageResponseTrollMapper> implements MessageResponseTrollService {
    public MessageResponseTrollServiceImpl(MessageResponseTrollRepository repository, MessageResponseTrollMapper mapper) {
        super(repository, mapper);
    }
}
