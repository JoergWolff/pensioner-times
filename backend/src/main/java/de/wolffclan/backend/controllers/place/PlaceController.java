package de.wolffclan.backend.controllers.place;

import de.wolffclan.backend.models.place.NewPlace;
import de.wolffclan.backend.models.place.Place;
import de.wolffclan.backend.services.place.PlaceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/places")
public class PlaceController {
    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllPlaces() {
        try {
            List<Place> getAllPlaces = placeService.getAllPlaces();
            return ResponseEntity.ok(getAllPlaces);
        } catch (NoSuchElementException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @ResponseBody
    @PostMapping
    public ResponseEntity<Object> postPlace(@RequestBody NewPlace newPlace) {
        try {
            Place savePlace = placeService.savePlace(newPlace);
            return ResponseEntity.ok(savePlace);
        } catch (NoSuchElementException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getPlaceById(@PathVariable String id) {
        try {
            Place searchPlace = placeService.getPlaceById(id);
            return ResponseEntity.ok(searchPlace);
        } catch (NoSuchElementException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> putPlace(@PathVariable String id, @RequestBody Place place) {
        try {
            Place savedPlace = placeService.updatePlace(id, place);
            return ResponseEntity.ok(savedPlace);
        } catch (NoSuchElementException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deletePlace(@PathVariable String id) {
        try {
            Place deletedPlace = placeService.deletePlace(id);
            return ResponseEntity.ok(deletedPlace);
        } catch (NoSuchElementException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }
}
