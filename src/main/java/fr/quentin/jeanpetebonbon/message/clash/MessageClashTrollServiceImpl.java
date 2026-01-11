package fr.quentin.jeanpetebonbon.message.clash;

import fr.quentin.jeanpetebonbon.core.generic.CrudServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageClashTrollServiceImpl extends CrudServiceImpl<MessageClashTroll, MessageClashTrollRepository, MessageClashTrollDto, MessageClashTrollMapper> implements MessageClashTrollService {
    public MessageClashTrollServiceImpl(MessageClashTrollRepository repository, MessageClashTrollMapper mapper) {
        super(repository, mapper, MessageClashTroll.class);
    }
}
