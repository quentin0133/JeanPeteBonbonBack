package fr.dawan.jeankasskouille.schedule;

import fr.dawan.jeankasskouille.generic.GenericController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("schedule")
public class ScheduleController extends GenericController<ScheduleDto, ScheduleService> {
    public ScheduleController(ScheduleService service) {
        super(service);
    }
}
