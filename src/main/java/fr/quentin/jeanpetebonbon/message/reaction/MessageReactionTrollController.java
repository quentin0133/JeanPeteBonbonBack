package fr.quentin.jeanpetebonbon.message.reaction;

import fr.quentin.jeanpetebonbon.core.logs.LogController;
import fr.quentin.jeanpetebonbon.message.reaction.dtos.MessageReactionTrollDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("messages-reaction-troll")
@RequiredArgsConstructor
public class MessageReactionTrollController {
    private final MessageReactionTrollService service;

    @LogController
    @GetMapping(params = {"page", "size"})
    public Page<MessageReactionTrollDto> findAllPage(Pageable pageable) {
        return service.findAll(pageable);
    }

    @LogController
    @GetMapping("/{id}")
    public MessageReactionTrollDto findById(@PathVariable long id) {
        return service.findById(id);
    }

    @LogController
    @PostMapping
    public ResponseEntity<MessageReactionTrollDto> save(@RequestBody MessageReactionTrollDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @LogController
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageReactionTrollDto> update(@PathVariable long id, @RequestBody MessageReactionTrollDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @LogController
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        service.deleteById(id);
    }
}
