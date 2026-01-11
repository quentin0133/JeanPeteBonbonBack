package fr.quentin.jeanpetebonbon.message.response;

import fr.quentin.jeanpetebonbon.message.response.dtos.MessageResponseTrollDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageResponseTrollMapperImpl implements MessageResponseTrollMapper {

    @Override
    public MessageResponseTrollDto toDto(MessageResponseTroll entity) {
        if (entity == null) {
            return null;
        }

        MessageResponseTrollDto messageResponseTrollDto = new MessageResponseTrollDto();

        messageResponseTrollDto.setId(entity.getId());
        messageResponseTrollDto.setVersion(entity.getVersion());
        messageResponseTrollDto.setMessageResponse(entity.getMessageResponse());

        return messageResponseTrollDto;
    }

    @Override
    public MessageResponseTroll toEntity(MessageResponseTrollDto dto) {
        if (dto == null) {
            return null;
        }

        MessageResponseTroll messageResponseTroll = new MessageResponseTroll();

        messageResponseTroll.setId(dto.getId());
        messageResponseTroll.setVersion(dto.getVersion());
        messageResponseTroll.setMessageResponse(dto.getMessageResponse());

        return messageResponseTroll;
    }
}
