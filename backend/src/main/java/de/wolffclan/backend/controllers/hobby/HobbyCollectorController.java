package de.wolffclan.backend.controllers.hobby;

import de.wolffclan.backend.models.hobby.HobbyCollector;
import de.wolffclan.backend.services.hobby.HobbyCollectorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/hobby-collectors")
public class HobbyCollectorController {
    private final HobbyCollectorService hobbyCollectorService;

    public HobbyCollectorController(HobbyCollectorService hobbyCollectorService) {
        this.hobbyCollectorService = hobbyCollectorService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllHobbyCollectors() {
        try {
            List<HobbyCollector> getAllHobbyCollectors = hobbyCollectorService.getAllHobbyCollectors();
            return ResponseEntity.ok(getAllHobbyCollectors);
        } catch (NoSuchElementException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
}
