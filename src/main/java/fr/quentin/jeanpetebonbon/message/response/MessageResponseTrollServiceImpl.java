package fr.quentin.jeanpetebonbon.message.response;

import fr.quentin.jeanpetebonbon.core.generic.CrudServiceImpl;
import fr.quentin.jeanpetebonbon.message.response.dtos.MessageResponseTrollDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageResponseTrollServiceImpl
    extends CrudServiceImpl<
            MessageResponseTroll,
            MessageResponseTrollRepository,
    MessageResponseTrollDto,
            MessageResponseTrollMapper>
    implements MessageResponseTrollService {
  public MessageResponseTrollServiceImpl(
      MessageResponseTrollRepository repository, MessageResponseTrollMapper mapper) {
    super(repository, mapper, MessageResponseTroll.class);
  }
}
