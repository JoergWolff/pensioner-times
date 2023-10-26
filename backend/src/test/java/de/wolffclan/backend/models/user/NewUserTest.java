package de.wolffclan.backend.models.user;

import de.wolffclan.backend.models.hobby.Hobby;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NewUserTest {

    @Test
    void isValid_ReturnTrue() {
        // GIVEN
        NewUser newUser = new NewUser("Hans", "Albers", "hans@testmail.de", LocalDate.now(), List.of(new Hobby("1", "Stricken", true, Instant.now(), Instant.now())));
        // WHEN
        boolean actual = newUser.isValid();
        // THEN
        boolean expected = true;

        assertEquals(expected, actual);
    }

    @Test
    void isValid_ReturnFalse() {
        // GIVEN
        NewUser newUser = new NewUser(null, "Albers", "hans@testmail.de", LocalDate.now(), List.of(new Hobby("1", "Stricken", true, Instant.now(), Instant.now())));
        // WHEN
        boolean actual = newUser.isValid();
        // THEN
        boolean expected = false;

        assertEquals(expected, actual);
    }
}