package fr.quentin.jeanpetebonbon.message.clash;

import fr.quentin.jeanpetebonbon.core.logs.LogController;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages-clash-troll")
@RequiredArgsConstructor
public class MessageClashTrollController {
    private final MessageClashTrollService service;

    @GetMapping(params = {"page", "size"})
    public Page<MessageClashTrollDto> findAllPage(Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public MessageClashTrollDto findById(@PathVariable long id) {
        return service.findById(id);
    }

    @PostMapping
    @LogController
    public ResponseEntity<MessageClashTrollDto> save(@RequestBody MessageClashTrollDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageClashTrollDto> update(@PathVariable long id, @RequestBody MessageClashTrollDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        service.deleteById(id);
    }
}
