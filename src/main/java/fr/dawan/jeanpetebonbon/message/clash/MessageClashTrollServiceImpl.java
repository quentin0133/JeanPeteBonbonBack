package fr.dawan.jeanpetebonbon.message.clash;

import fr.dawan.jeanpetebonbon.core.generic.GenericServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageClashTrollServiceImpl extends GenericServiceImpl<MessageClashTroll, MessageClashTrollRepository, MessageClashTrollDto, MessageClashTrollMapper> implements MessageClashTrollService {
    public MessageClashTrollServiceImpl(MessageClashTrollRepository repository, MessageClashTrollMapper mapper) {
        super(repository, mapper);
    }
}
