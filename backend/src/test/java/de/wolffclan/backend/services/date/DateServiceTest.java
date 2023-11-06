package de.wolffclan.backend.services.date;

import de.wolffclan.backend.models.date.Date;
import de.wolffclan.backend.models.date.NewDate;
import de.wolffclan.backend.repositories.date.DateRepository;
import de.wolffclan.backend.repositories.user.UserRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DateServiceTest {
    DateRepository dateRepository = mock(DateRepository.class);
    UserRepository userRepository = mock(UserRepository.class);
    DateService dateService = new DateService(dateRepository, userRepository);

    @Test
    void getAllDates() {
        // GIVEN
        Date date = createDate();
        List<Date> expected = new ArrayList<>(List.of(date));
        // WHEN
        when(dateRepository.findAll()).thenReturn(expected);
        List<Date> actual = dateService.getAllDates();
        verify(dateRepository).findAll();
        assertEquals(expected, actual);
    }

    @Test
    void saveDate_ReturnsDate() {
        // GIVEN
        NewDate newDate = new NewDate(
                createLocalDate("2023-02-23"),
                createLocalTime("12:30"),
                "TestDate",
                4,
                1,
                List.of("123"),
                "123");
        // WHEN
        when(userRepository.existsById(any())).thenReturn(true);
        when(dateRepository.save(any(Date.class)))
                .thenReturn(
                        new Date("123",
                                newDate.meetingDay(),
                                newDate.meetingTime(),
                                newDate.description(),
                                newDate.maxUser(),
                                newDate.userCounter(),
                                newDate.userIds(),
                                newDate.placeId()
                        ));
        Date savedDate = dateService.saveDate(newDate);
        // THEN
        assertNotNull(savedDate);
        assertEquals(newDate.meetingDay(), savedDate.meetingDay());
        assertEquals(newDate.meetingTime(), savedDate.meetingTime());
        assertEquals(newDate.description(), savedDate.description());
        assertEquals(newDate.maxUser(), savedDate.maxUser());
        assertEquals(newDate.userCounter(), savedDate.userCounter());
        assertEquals(newDate.userIds(), savedDate.userIds());
        assertEquals(newDate.placeId(), savedDate.placeId());
        verify(dateRepository, times(1)).save(any(Date.class));
    }

    @Test
    void saveDateReturnsTooFewInput() {
        // GIVEN
        NewDate newDate = new NewDate(
                createLocalDate("2023-02-23"),
                createLocalTime("12:30"),
                "TestDate",
                4,
                1,
                List.of("123"),
                null);
        // WHEN
        assertThrows(NoSuchElementException.class, () -> dateService.saveDate(newDate));
        // THEN
        verify(dateRepository, never()).save(any(Date.class));
    }

    @Test
    void saveDate_ReturnsNoUserId() {
        // GIVEN
        NewDate newDate = new NewDate(
                createLocalDate("2023-02-23"),
                createLocalTime("12:30"),
                "TestDate",
                4,
                1,
                List.of("123"),
                "123");
        // WHEN
        when(userRepository.existsById(any())).thenReturn(false);
        assertThrows(NoSuchElementException.class, () -> dateService.saveDate(newDate));
        // THEN
        verify(dateRepository, never()).save(any(Date.class));
        verify(dateRepository, never()).existsById(anyString());
    }

    @Test
    void getDateById_ReturnsValidDate() {
        // GIVEN
        String dateId = "123";
        Date searchDate = new Date(
                dateId,
                createLocalDate("2023-02-23"),
                createLocalTime("12:30"),
                "TestDate",
                4,
                1,
                List.of("123"),
                "123"
        );
        // WHEN
        when(dateRepository.findById(dateId)).thenReturn(Optional.of(searchDate));
        Date date = dateService.getDateById(dateId);
        // THEN
        assertNotNull(date);
        assertEquals(dateId, date.id());
        assertEquals(searchDate.meetingDay(), date.meetingDay());
        assertEquals(searchDate.meetingTime(), date.meetingTime());
        assertEquals(searchDate.description(), date.description());
        assertEquals(searchDate.maxUser(), date.maxUser());
        assertEquals(searchDate.userCounter(), date.userCounter());
        assertEquals(searchDate.userIds(), date.userIds());
        assertEquals(searchDate.placeId(), date.placeId());
        verify(dateRepository, times(1)).findById(dateId);
    }

    @Test
    void getDateById_ReturnsInvalidId() {
        // GIVEN
        String invalidId = "invalid";
        // WHEN
        when(dateRepository.findById(invalidId)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> dateService.getDateById(invalidId));
        // THEN
        verify(dateRepository, times(1)).findById(invalidId);
    }

    @Test
    void updateDate_ReturnsUpdatedDate() {
        // GIVEN
        Date existingDate = createDate();
        Date updateForDate = new Date(
                "1",
                createLocalDate("2023-02-23"),
                createLocalTime("12:30"),
                "TestDate",
                4,
                3,
                List.of("123", "456", "789"),
                "123"
        );
        // WHEN
        when(dateRepository.existsById(existingDate.id())).thenReturn(true);
        when(dateRepository.findById(existingDate.id())).thenReturn(Optional.of(existingDate));
        when(userRepository.existsById(any())).thenReturn(true);
        Date updatedDate = dateService.updateDate(existingDate.id(), updateForDate);
        // THEN
        assertNotNull(updatedDate);
        assertEquals(updateForDate.id(), updatedDate.id());
        assertEquals(updateForDate.meetingDay(), updatedDate.meetingDay());
        assertEquals(updateForDate.meetingTime(), updatedDate.meetingTime());
        assertEquals(updateForDate.description(), updatedDate.description());
        assertEquals(updateForDate.maxUser(), updatedDate.maxUser());
        assertEquals(updateForDate.userCounter(), updatedDate.userCounter());
        assertEquals(updateForDate.userIds(), updatedDate.userIds());
        assertEquals(updateForDate.placeId(), updatedDate.placeId());
        verify(dateRepository, times(1)).save(any(Date.class));
    }

    @Test
    void updateDate_ReturnsNoSuchElementException_TooMuchUser() {
        // GIVEN
        Date existingDate = createDate();
        Date updateForDate = new Date(
                "1",
                createLocalDate("2023-02-23"),
                createLocalTime("12:30"),
                "TestDate",
                4,
                5,
                List.of("123", "456", "789", "112", "113"),
                "123"
        );
        // WHEN
        when(dateRepository.existsById(existingDate.id())).thenReturn(true);
        when(dateRepository.findById(existingDate.id())).thenReturn(Optional.of(existingDate));
        when(userRepository.existsById(any())).thenReturn(true);
        String expected = "Too much Users. Maximize the Users to 5.";
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> dateService.updateDate("1", updateForDate));
        // THEN
        assertEquals(expected, exception.getMessage());
        verify(dateRepository, never()).save(any(Date.class));
    }

    @Test
    void updateDate_ReturnsNoSuchElementException_NoDateId() {
        // GIVEN
        Date updateForDate = new Date(
                "1",
                createLocalDate("2023-02-23"),
                createLocalTime("12:30"),
                "TestDate",
                4,
                5,
                List.of("123", "456", "789", "112", "113"),
                "123"
        );
        // WHEN
        when(dateRepository.existsById("1")).thenReturn(false);
        // THEN
        assertThrows(NoSuchElementException.class, () -> dateService.updateDate("1", updateForDate));
        verify(dateRepository, never()).save(any());
    }

    @Test
    void deleteDate_ReturnsDeletedDate() {
        // GIVEN
        Date existingDate = createDate();
        // WHEN
        when(dateRepository.existsById(existingDate.id())).thenReturn(true);
        when(dateRepository.findById(existingDate.id())).thenReturn(Optional.of(existingDate));
        Date deletedDate = dateService.deleteDate(existingDate.id());
        // THEN
        assertNotNull(deletedDate);
        assertEquals(existingDate.id(), deletedDate.id());
        verify(dateRepository, times(1)).deleteById(existingDate.id());
    }

    @Test
    void deleteDate_ReturnsNoSuchElementException() {
        // GIVEN
        String invalidId = "invalid";
        // WHEN
        when(dateRepository.existsById(invalidId)).thenReturn(false);
        assertThrows(NoSuchElementException.class, () -> dateService.deleteDate(invalidId));
        // THEN
        verify(dateRepository, never()).deleteById(invalidId);
    }

    private static Date createDate() {
        return new Date(
                "1",
                createLocalDate("2023-02-23"),
                createLocalTime("12:30"),
                "TestDate",
                4,
                1,
                List.of("123"),
                "123"
        );
    }

    private static LocalDate createLocalDate(String localDateAsString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(localDateAsString, formatter);
    }

    private static LocalTime createLocalTime(String timeAsString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(timeAsString, formatter);
    }
}