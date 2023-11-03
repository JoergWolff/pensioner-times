package de.wolffclan.backend.services.place;

import de.wolffclan.backend.models.place.NewPlace;
import de.wolffclan.backend.models.place.Place;
import de.wolffclan.backend.repositories.place.PlaceRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlaceServiceTest {
    PlaceRepository placeRepository = mock(PlaceRepository.class);
    PlaceService placeService = new PlaceService(placeRepository);

    @Test
    void getAllPlaces() {
        // GIVEN
        Place place = new Place("1", "Test", "12345", "Teststraße 123", "Very testy");
        List<Place> expected = new ArrayList<>(List.of(place));
        // WHEN
        when(placeRepository.findAll()).thenReturn(expected);
        List<Place> actual = placeService.getAllPlaces();
        // THEN
        verify(placeRepository).findAll();
        assertEquals(expected, actual);
    }

    @Test
    void savePlace_ReturnsPLace() {
        // GIVEN
        NewPlace newPlace = new NewPlace("Test", "12345", "Teststraße 123", "Very testy");
        // WHEN
        when(placeRepository.save(any(Place.class))).thenReturn(
                new Place("1",
                        newPlace.town(),
                        newPlace.postalCode(),
                        newPlace.street(),
                        newPlace.description()));
        Place savedPlace = placeService.savePlace(newPlace);
        assertNotNull(savedPlace);
        assertEquals(newPlace.town(), savedPlace.town());
        assertEquals(newPlace.postalCode(), savedPlace.postalCode());
        assertEquals(newPlace.street(), savedPlace.street());
        assertEquals(newPlace.description(), savedPlace.description());
        verify(placeRepository, times(1)).save(any(Place.class));

    }

    @Test
    void savePlace_ReturnsTooFewInputs() {
        // GIVEN
        NewPlace newPlace = new NewPlace(null, "12345", "Teststraße 123", "Very testy");
        // WHEN
        assertThrows(NoSuchElementException.class, () -> placeService.savePlace(newPlace));
        // THEN
        verify(placeRepository, never()).save(any(Place.class));
    }

    @Test
    void getPlaceById_ReturnsValidPlace() {
        // GIVEN
        String placeId = "123";
        Place searchPlace = new Place(placeId, "Test", "12345", "Teststraße 123", "Very testy");
        // WHEN
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(searchPlace));
        Place place = placeService.getPlaceById(placeId);
        // THEN
        assertNotNull(place);
        assertEquals(placeId, place.id());
        assertEquals(searchPlace.town(), place.town());
        assertEquals(searchPlace.postalCode(), place.postalCode());
        assertEquals(searchPlace.street(), place.street());
        assertEquals(searchPlace.description(), place.description());
    }

    @Test
    void getPlaceById_ReturnsInvalidId() {
        // GIVEN
        String invalidId = "invalid";
        // WHEN
        when(placeRepository.findById(invalidId)).thenReturn(Optional.empty());
        // THEN
        assertThrows(NoSuchElementException.class, () -> placeService.getPlaceById(invalidId));
        verify(placeRepository, times(1)).findById(invalidId);
    }

    @Test
    void updatePlace_ReturnsUpdatedPlace() {
        // GIVEN
        Place existingPlace = new Place("123", "Test", "12345", "Teststraße 123", "Very testy");
        Place updateForPlace = new Place("123", "TestUpdate", "12345", "Teststraße 123", "Very testy");
        // WHEN
        when(placeRepository.existsById("123")).thenReturn(true);
        when(placeRepository.findById("123")).thenReturn(Optional.of(existingPlace));
        Place updatedPlace = placeService.updatePlace("123", updateForPlace);
        // THEN
        assertNotNull(updatedPlace);
        assertEquals(updateForPlace.town(), updatedPlace.town());
        assertEquals(updateForPlace.postalCode(), updatedPlace.postalCode());
        assertEquals(updateForPlace.street(), updatedPlace.street());
        assertEquals(updateForPlace.description(), updatedPlace.description());
        verify(placeRepository, times(1)).save(any(Place.class));
    }

    @Test
    void updatedPlace_ReturnsNoSuchElementException() {
        // GIVEN
        Place updateForPlace = new Place("123", "TestUpdate", "12345", "Teststraße 123", "Very testy");
        // WHEN
        when(placeRepository.existsById("123")).thenReturn(false);
        // THEN
        assertThrows(NoSuchElementException.class, () -> placeService.updatePlace("123", updateForPlace));
        verify(placeRepository, never()).save(any());
    }

    @Test
    void deletePlace_ReturnsDeletedUser() {
        // GIVEN
        Place existingPlace = new Place("123", "Test", "12345", "Teststraße 123", "Very testy");
        // WHEN
        when(placeRepository.existsById("123")).thenReturn(true);
        when(placeRepository.findById("123")).thenReturn(Optional.of(existingPlace));
        Place deletedPlace = placeService.deletePlace("123");
        // THEN
        assertNotNull(deletedPlace);
        assertEquals("123", deletedPlace.id());
        assertEquals(existingPlace.town(), deletedPlace.town());

        verify(placeRepository, times(1)).deleteById("123");
    }

    @Test
    void deletePlace_ReturnsNoSuchElementException() {
        // GIVEN
        // WHEN
        when(placeRepository.existsById("123")).thenReturn(false);
        // THEN
        assertThrows(NoSuchElementException.class, () -> placeService.deletePlace("123"));
        verify(placeRepository, never()).deleteById(any());
    }
}