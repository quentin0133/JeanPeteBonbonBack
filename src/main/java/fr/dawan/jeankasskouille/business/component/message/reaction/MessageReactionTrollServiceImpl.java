package fr.dawan.jeankasskouille.business.component.message.reaction;

import fr.dawan.jeankasskouille.generic.GenericServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageReactionTrollServiceImpl extends GenericServiceImpl<MessageReactionTroll, MessageReactionTrollRepository, MessageReactionTrollDto, MessageReactionTrollMapper> implements MessageReactionTrollService {
    public MessageReactionTrollServiceImpl(MessageReactionTrollRepository repository, MessageReactionTrollMapper mapper) {
        super(repository, mapper);
    }
}
