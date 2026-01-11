package fr.quentin.jeanpetebonbon.ping;

import fr.quentin.jeanpetebonbon.core.logs.LogController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PingController {
    @LogController
    @GetMapping
    ResponseEntity<Void> ping() {
        return ResponseEntity.ok().build();
    }
}
