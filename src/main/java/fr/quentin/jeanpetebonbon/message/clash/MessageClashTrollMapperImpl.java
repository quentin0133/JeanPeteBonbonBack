package fr.quentin.jeanpetebonbon.message.clash;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageClashTrollMapperImpl implements MessageClashTrollMapper {

    @Override
    public MessageClashTrollDto toDto(MessageClashTroll entity) {
        if (entity == null) {
            return null;
        }

        MessageClashTrollDto messageClashTrollDto = new MessageClashTrollDto();

        messageClashTrollDto.setId(entity.getId());
        messageClashTrollDto.setVersion(entity.getVersion());
        messageClashTrollDto.setMessageClash(entity.getMessageClash());

        return messageClashTrollDto;
    }

    @Override
    public MessageClashTroll toEntity(MessageClashTrollDto dto) {
        if (dto == null) {
            return null;
        }

        MessageClashTroll messageClashTroll = new MessageClashTroll();

        messageClashTroll.setId(dto.getId());
        messageClashTroll.setVersion(dto.getVersion());
        messageClashTroll.setMessageClash(dto.getMessageClash());

        return messageClashTroll;
    }
}
