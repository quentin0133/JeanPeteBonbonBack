package fr.quentin.jeanpetebonbon.bot.guild;

import fr.quentin.jeanpetebonbon.bot.guild.dtos.GuildDto;
import fr.quentin.jeanpetebonbon.core.logs.LogController;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guilds")
@RequiredArgsConstructor
public class GuildController {
    private final GuildService service;

    @LogController
    @GetMapping(params = {"page", "size"})
    public Page<GuildDto> findAllPage(Pageable pageable) {
        return service.findAll(pageable);
    }

    @LogController
    @GetMapping("/{id}")
    public GuildDto findById(@PathVariable long id) {
        return service.findById(id);
    }

    @LogController
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GuildDto> save(@RequestBody GuildDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @LogController
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GuildDto> update(@PathVariable long id, @RequestBody GuildDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @LogController
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        service.deleteById(id);
    }
}
