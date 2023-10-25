package de.wolffclan.backend.models.hobby;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NewHobbyTest {

    @Test
    void isValid_ReturnTrue() {
        // GIVEN
        NewHobby newHobby = new NewHobby("Stricken");
        // WHEN
        boolean actual = newHobby.isValid();
        // THEN
        boolean expected = true;

        assertEquals(expected, actual);

    }

    @Test
    void isValid_ReturnFalse() {
        // GIVEN
        NewHobby newHobby = new NewHobby(null);
        // WHEN
        boolean actual = newHobby.isValid();
        // THEN
        boolean expected = false;

        assertEquals(expected, actual);

    }
}