package fr.quentin.jeanpetebonbon.message.reaction;

import fr.quentin.jeanpetebonbon.message.reaction.dtos.MessageReactionTrollDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageReactionTrollMapperImpl implements MessageReactionTrollMapper {

    @Override
    public MessageReactionTrollDto toDto(MessageReactionTroll entity) {
        if (entity == null) {
            return null;
        }

        MessageReactionTrollDto messageReactionTrollDto = new MessageReactionTrollDto();

        messageReactionTrollDto.setId(entity.getId());
        messageReactionTrollDto.setVersion(entity.getVersion());
        messageReactionTrollDto.setMessageResponse(entity.getMessageResponse());

        return messageReactionTrollDto;
    }

    @Override
    public MessageReactionTroll toEntity(MessageReactionTrollDto dto) {
        if (dto == null) {
            return null;
        }

        MessageReactionTroll messageReactionTroll = new MessageReactionTroll();

        messageReactionTroll.setId(dto.getId());
        messageReactionTroll.setVersion(dto.getVersion());
        messageReactionTroll.setMessageResponse(dto.getMessageResponse());

        return messageReactionTroll;
    }
}
