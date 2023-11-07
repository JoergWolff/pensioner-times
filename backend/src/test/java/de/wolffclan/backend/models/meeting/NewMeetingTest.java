package de.wolffclan.backend.models.meeting;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NewMeetingTest {

    @Test
    void isValid_ReturnsTrue() {
        // GIVEN
        NewMeeting newMeeting = new NewMeeting(LocalDate.now(), LocalTime.now(), "Test", 4, 4, List.of("123"), "123");
        // WHEN
        boolean actual = newMeeting.isValid();
        // THEN
        boolean expected = true;
        assertEquals(expected, actual);
    }
    @Test
    void isValid_ReturnsFalse() {
        // GIVEN
        NewMeeting newMeeting = new NewMeeting(LocalDate.now(), LocalTime.now(), "Test", 4, 4, List.of("123"), null);
        // WHEN
        boolean actual = newMeeting.isValid();
        // THEN
        boolean expected = false;
        assertEquals(expected, actual);
    }
}