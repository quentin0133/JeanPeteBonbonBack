package fr.dawan.jeankasskouille.business.component.message.trigger;

import fr.dawan.jeankasskouille.generic.GenericServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageTriggerTrollServiceImpl extends GenericServiceImpl<MessageTriggerTroll, MessageTriggerTrollRepository, MessageTriggerTrollDto, MessageTriggerTrollMapper> implements MessageTriggerTrollService {
    public MessageTriggerTrollServiceImpl(MessageTriggerTrollRepository repository, MessageTriggerTrollMapper mapper) {
        super(repository, mapper);
    }
}
