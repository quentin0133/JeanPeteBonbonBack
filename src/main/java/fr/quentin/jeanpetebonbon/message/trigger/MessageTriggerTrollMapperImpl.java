package fr.quentin.jeanpetebonbon.message.trigger;

import fr.quentin.jeanpetebonbon.message.response.MessageResponseTroll;
import fr.quentin.jeanpetebonbon.message.response.dtos.MessageResponseTrollDto;
import fr.quentin.jeanpetebonbon.message.trigger.dtos.MessageTriggerTrollDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class MessageTriggerTrollMapperImpl implements MessageTriggerTrollMapper {

    @Override
    public MessageTriggerTrollDto toDto(MessageTriggerTroll entity) {
        if (entity == null) {
            return null;
        }

        MessageTriggerTrollDto messageTriggerTrollDto = new MessageTriggerTrollDto();

        messageTriggerTrollDto.setId(entity.getId());
        messageTriggerTrollDto.setVersion(entity.getVersion());
        messageTriggerTrollDto.setMessageTrigger(entity.getMessageTrigger());
        messageTriggerTrollDto.setMessagesResponseTroll(messageResponseTrollListToMessageResponseTrollDtoList(entity.getMessagesResponseTroll()));

        return messageTriggerTrollDto;
    }

    @Override
    public MessageTriggerTroll toEntity(MessageTriggerTrollDto dto) {
        if (dto == null) {
            return null;
        }

        MessageTriggerTroll messageTriggerTroll = new MessageTriggerTroll();

        messageTriggerTroll.setId(dto.getId());
        messageTriggerTroll.setVersion(dto.getVersion());
        messageTriggerTroll.setMessageTrigger(dto.getMessageTrigger());
        messageTriggerTroll.setMessagesResponseTroll(messageResponseTrollDtoListToMessageResponseTrollList(dto.getMessagesResponseTroll()));

        return messageTriggerTroll;
    }

    protected MessageResponseTrollDto messageResponseTrollToMessageResponseTrollDto(MessageResponseTroll messageResponseTroll) {
        if (messageResponseTroll == null) {
            return null;
        }

        MessageResponseTrollDto messageResponseTrollDto = new MessageResponseTrollDto();

        messageResponseTrollDto.setId(messageResponseTroll.getId());
        messageResponseTrollDto.setVersion(messageResponseTroll.getVersion());
        messageResponseTrollDto.setMessageResponse(messageResponseTroll.getMessageResponse());

        return messageResponseTrollDto;
    }

    protected List<MessageResponseTrollDto> messageResponseTrollListToMessageResponseTrollDtoList(List<MessageResponseTroll> list) {
        if (list == null) {
            return null;
        }

        List<MessageResponseTrollDto> list1 = new ArrayList<>(list.size());
        for (MessageResponseTroll messageResponseTroll : list) {
            list1.add(messageResponseTrollToMessageResponseTrollDto(messageResponseTroll));
        }

        return list1;
    }

    protected MessageResponseTroll messageResponseTrollDtoToMessageResponseTroll(MessageResponseTrollDto messageResponseTrollDto) {
        if (messageResponseTrollDto == null) {
            return null;
        }

        MessageResponseTroll messageResponseTroll = new MessageResponseTroll();

        messageResponseTroll.setId(messageResponseTrollDto.getId());
        messageResponseTroll.setVersion(messageResponseTrollDto.getVersion());
        messageResponseTroll.setMessageResponse(messageResponseTrollDto.getMessageResponse());

        return messageResponseTroll;
    }

    protected List<MessageResponseTroll> messageResponseTrollDtoListToMessageResponseTrollList(List<MessageResponseTrollDto> list) {
        if (list == null) {
            return null;
        }

        List<MessageResponseTroll> list1 = new ArrayList<>(list.size());
        for (MessageResponseTrollDto messageResponseTrollDto : list) {
            list1.add(messageResponseTrollDtoToMessageResponseTroll(messageResponseTrollDto));
        }

        return list1;
    }
}
