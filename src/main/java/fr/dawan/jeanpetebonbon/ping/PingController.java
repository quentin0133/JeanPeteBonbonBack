package fr.dawan.jeanpetebonbon.ping;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class PingController {
    @GetMapping
    ResponseEntity<Void> ping() {
        return ResponseEntity.noContent().build();
    }
}
