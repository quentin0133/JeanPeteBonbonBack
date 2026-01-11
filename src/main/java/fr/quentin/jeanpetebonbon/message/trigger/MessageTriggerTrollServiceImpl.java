package fr.quentin.jeanpetebonbon.message.trigger;

import fr.quentin.jeanpetebonbon.core.generic.CrudServiceImpl;
import fr.quentin.jeanpetebonbon.message.trigger.dtos.MessageTriggerTrollDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageTriggerTrollServiceImpl extends CrudServiceImpl<MessageTriggerTroll, MessageTriggerTrollRepository, MessageTriggerTrollDto, MessageTriggerTrollMapper> implements MessageTriggerTrollService {
    public MessageTriggerTrollServiceImpl(MessageTriggerTrollRepository repository, MessageTriggerTrollMapper mapper) {
        super(repository, mapper, MessageTriggerTroll.class);
    }
}
