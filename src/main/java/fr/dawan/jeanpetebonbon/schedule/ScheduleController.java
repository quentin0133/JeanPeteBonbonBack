package fr.dawan.jeanpetebonbon.schedule;

import fr.dawan.jeanpetebonbon.schedule.dtos.ScheduleDto;
import fr.dawan.jeanpetebonbon.core.generic.GenericController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("schedules")
public class ScheduleController extends GenericController<ScheduleDto, ScheduleService> {
    public ScheduleController(ScheduleService service) {
        super(service);
    }
}
