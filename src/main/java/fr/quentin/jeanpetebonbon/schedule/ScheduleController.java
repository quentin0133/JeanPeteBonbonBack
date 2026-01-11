package fr.quentin.jeanpetebonbon.schedule;

import fr.quentin.jeanpetebonbon.core.logs.LogController;
import fr.quentin.jeanpetebonbon.schedule.dtos.ScheduleLightDto;
import fr.quentin.jeanpetebonbon.schedule.services.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService service;

    @LogController
    @GetMapping
    public Page<ScheduleLightDto> findAllPage(Pageable pageable) {
        return service.findAll(pageable);
    }

    @LogController
    @GetMapping("/{id}")
    public ScheduleLightDto findById(@PathVariable long id) {
        return service.findById(id);
    }

    @LogController
    @PostMapping
    public ResponseEntity<ScheduleLightDto> save(@RequestBody ScheduleLightDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @LogController
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ScheduleLightDto> update(@PathVariable long id, @RequestBody ScheduleLightDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @LogController
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        service.deleteById(id);
    }
}
