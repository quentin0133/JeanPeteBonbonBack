package fr.quentin.jeanpetebonbon.message.response;

import fr.quentin.jeanpetebonbon.core.logs.LogController;
import fr.quentin.jeanpetebonbon.message.response.dtos.MessageResponseTrollDto;
import fr.quentin.jeanpetebonbon.message.trigger.MessageTriggerTrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages-response-troll")
@RequiredArgsConstructor
public class MessageResponseTrollController {
    private final MessageResponseTrollService service;

    @LogController
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<MessageResponseTrollDto> findAllPage(Pageable pageable) {
        return service.findAll(pageable);
    }

    @LogController
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageResponseTrollDto findById(@PathVariable long id) {
        return service.findById(id);
    }

    @LogController
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponseTrollDto> save(@RequestBody MessageResponseTrollDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @LogController
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponseTrollDto> update(@PathVariable long id, @RequestBody MessageResponseTrollDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @LogController
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        service.deleteById(id);
    }
}
