package de.wolffclan.backend.models.place;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NewPlaceTest {

    @Test
    void isValid_ReturnsTrue() {
        // GIVEN
        NewPlace newPlace = new NewPlace("Test", "12345", "TestStreet", "Very Testy");
        // WHEN
        boolean actual = newPlace.isValid();
        // THEN
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    void isValid_ReturnsFalse() {
        // GIVEN
        NewPlace newPlace = new NewPlace(null, "12345", "TestStreet", "Very Testy");
        // WHEN
        boolean actual = newPlace.isValid();
        // THEN
        boolean expected = false;
        assertEquals(expected, actual);
    }
}