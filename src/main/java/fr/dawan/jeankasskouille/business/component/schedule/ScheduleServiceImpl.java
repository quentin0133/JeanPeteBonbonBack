package fr.dawan.jeankasskouille.business.component.schedule;

import fr.dawan.jeankasskouille.business.component.bot.BotService;
import fr.dawan.jeankasskouille.business.component.schedule.dtos.ScheduleDto;
import fr.dawan.jeankasskouille.generic.GenericServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ScheduleServiceImpl extends GenericServiceImpl<Schedule, ScheduleRepository, ScheduleDto, ScheduleMapper> implements ScheduleService {
    public ScheduleServiceImpl(ScheduleRepository repository, ScheduleMapper mapper, BotService botService) {
        super(repository, mapper);
    }
}
