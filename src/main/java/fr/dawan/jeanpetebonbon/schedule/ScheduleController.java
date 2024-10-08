package fr.dawan.jeanpetebonbon.schedule;

import fr.dawan.jeanpetebonbon.schedule.dtos.ScheduleDto;
import fr.dawan.jeanpetebonbon.core.generic.GenericController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("schedules")
public class ScheduleController extends GenericController<Schedule, ScheduleDto, ScheduleService> {
    public ScheduleController(ScheduleService service) {
        super(service);
    }
}
