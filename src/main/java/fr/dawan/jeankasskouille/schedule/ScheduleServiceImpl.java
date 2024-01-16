package fr.dawan.jeankasskouille.schedule;

import fr.dawan.jeankasskouille.generic.GenericServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ScheduleServiceImpl extends GenericServiceImpl<Schedule, ScheduleRepository,ScheduleDto, ScheduleMapper> implements ScheduleService {
    public ScheduleServiceImpl(ScheduleRepository repository, ScheduleMapper mapper) {
        super(repository, mapper);
    }
}
