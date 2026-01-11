package fr.quentin.jeanpetebonbon.message.reaction;

import fr.quentin.jeanpetebonbon.core.generic.CrudServiceImpl;
import fr.quentin.jeanpetebonbon.message.reaction.dtos.MessageReactionTrollDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageReactionTrollServiceImpl extends CrudServiceImpl<MessageReactionTroll, MessageReactionTrollRepository, MessageReactionTrollDto, MessageReactionTrollMapper> implements MessageReactionTrollService {
    public MessageReactionTrollServiceImpl(MessageReactionTrollRepository repository, MessageReactionTrollMapper mapper) {
        super(repository, mapper, MessageReactionTroll.class);
    }
}
