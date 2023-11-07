package de.wolffclan.backend.services.meeting;

import de.wolffclan.backend.models.meeting.Meeting;
import de.wolffclan.backend.models.meeting.NewMeeting;
import de.wolffclan.backend.repositories.meeting.MeetingRepository;
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

class MeetingServiceTest {
    MeetingRepository meetingRepository = mock(MeetingRepository.class);
    UserRepository userRepository = mock(UserRepository.class);
    MeetingService meetingService = new MeetingService(meetingRepository, userRepository);

    @Test
    void getAllMeetings() {
        // GIVEN
        Meeting meeting = createMeeting();
        List<Meeting> expected = new ArrayList<>(List.of(meeting));
        // WHEN
        when(meetingRepository.findAll()).thenReturn(expected);
        List<Meeting> actual = meetingService.getAllMeetings();
        verify(meetingRepository).findAll();
        assertEquals(expected, actual);
    }

    @Test
    void saveMeeting_ReturnsMeeting() {
        // GIVEN
        NewMeeting newMeeting = new NewMeeting(
                createLocalDate("2023-02-23"),
                createLocalTime("12:30"),
                "TestDate",
                4,
                1,
                List.of("123"),
                "123");
        // WHEN
        when(userRepository.existsById(any())).thenReturn(true);
        when(meetingRepository.save(any(Meeting.class)))
                .thenReturn(
                        new Meeting("123",
                                newMeeting.meetingDay(),
                                newMeeting.meetingTime(),
                                newMeeting.description(),
                                newMeeting.maxUser(),
                                newMeeting.userCounter(),
                                newMeeting.userIds(),
                                newMeeting.placeId()
                        ));
        Meeting savedMeeting = meetingService.saveMeeting(newMeeting);
        // THEN
        assertNotNull(savedMeeting);
        assertEquals(newMeeting.meetingDay(), savedMeeting.meetingDay());
        assertEquals(newMeeting.meetingTime(), savedMeeting.meetingTime());
        assertEquals(newMeeting.description(), savedMeeting.description());
        assertEquals(newMeeting.maxUser(), savedMeeting.maxUser());
        assertEquals(newMeeting.userCounter(), savedMeeting.userCounter());
        assertEquals(newMeeting.userIds(), savedMeeting.userIds());
        assertEquals(newMeeting.placeId(), savedMeeting.placeId());
        verify(meetingRepository, times(1)).save(any(Meeting.class));
    }

    @Test
    void saveMeeting_ReturnsTooFewInput() {
        // GIVEN
        NewMeeting newMeeting = new NewMeeting(
                createLocalDate("2023-02-23"),
                createLocalTime("12:30"),
                "TestDate",
                4,
                1,
                List.of("123"),
                null);
        // WHEN
        assertThrows(NoSuchElementException.class, () -> meetingService.saveMeeting(newMeeting));
        // THEN
        verify(meetingRepository, never()).save(any(Meeting.class));
    }

    @Test
    void saveMeeting_ReturnsNoUserId() {
        // GIVEN
        NewMeeting newMeeting = new NewMeeting(
                createLocalDate("2023-02-23"),
                createLocalTime("12:30"),
                "TestDate",
                4,
                1,
                List.of("123"),
                "123");
        // WHEN
        when(userRepository.existsById(any())).thenReturn(false);
        assertThrows(NoSuchElementException.class, () -> meetingService.saveMeeting(newMeeting));
        // THEN
        verify(meetingRepository, never()).save(any(Meeting.class));
        verify(meetingRepository, never()).existsById(anyString());
    }

    @Test
    void getMeetingById_ReturnsValidDate() {
        // GIVEN
        String dateId = "123";
        Meeting searchMeeting = new Meeting(
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
        when(meetingRepository.findById(dateId)).thenReturn(Optional.of(searchMeeting));
        Meeting meeting = meetingService.getMeetingById(dateId);
        // THEN
        assertNotNull(meeting);
        assertEquals(dateId, meeting.id());
        assertEquals(searchMeeting.meetingDay(), meeting.meetingDay());
        assertEquals(searchMeeting.meetingTime(), meeting.meetingTime());
        assertEquals(searchMeeting.description(), meeting.description());
        assertEquals(searchMeeting.maxUser(), meeting.maxUser());
        assertEquals(searchMeeting.userCounter(), meeting.userCounter());
        assertEquals(searchMeeting.userIds(), meeting.userIds());
        assertEquals(searchMeeting.placeId(), meeting.placeId());
        verify(meetingRepository, times(1)).findById(dateId);
    }

    @Test
    void getMeetingById_ReturnsInvalidId() {
        // GIVEN
        String invalidId = "invalid";
        // WHEN
        when(meetingRepository.findById(invalidId)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> meetingService.getMeetingById(invalidId));
        // THEN
        verify(meetingRepository, times(1)).findById(invalidId);
    }

    @Test
    void updateMeeting_ReturnsUpdatedMeeting() {
        // GIVEN
        Meeting existingMeeting = createMeeting();
        Meeting updateForMeeting = new Meeting(
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
        when(meetingRepository.existsById(existingMeeting.id())).thenReturn(true);
        when(meetingRepository.findById(existingMeeting.id())).thenReturn(Optional.of(existingMeeting));
        when(userRepository.existsById(any())).thenReturn(true);
        Meeting updatedMeeting = meetingService.updateMeeting(existingMeeting.id(), updateForMeeting);
        // THEN
        assertNotNull(updatedMeeting);
        assertEquals(updateForMeeting.id(), updatedMeeting.id());
        assertEquals(updateForMeeting.meetingDay(), updatedMeeting.meetingDay());
        assertEquals(updateForMeeting.meetingTime(), updatedMeeting.meetingTime());
        assertEquals(updateForMeeting.description(), updatedMeeting.description());
        assertEquals(updateForMeeting.maxUser(), updatedMeeting.maxUser());
        assertEquals(updateForMeeting.userCounter(), updatedMeeting.userCounter());
        assertEquals(updateForMeeting.userIds(), updatedMeeting.userIds());
        assertEquals(updateForMeeting.placeId(), updatedMeeting.placeId());
        verify(meetingRepository, times(1)).save(any(Meeting.class));
    }

    @Test
    void updateMeeting_ReturnsNoSuchElementException_TooMuchUser() {
        // GIVEN
        Meeting existingMeeting = createMeeting();
        Meeting updateForMeeting = new Meeting(
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
        when(meetingRepository.existsById(existingMeeting.id())).thenReturn(true);
        when(meetingRepository.findById(existingMeeting.id())).thenReturn(Optional.of(existingMeeting));
        when(userRepository.existsById(any())).thenReturn(true);
        String expected = "Too much Users. Maximize the Users to 5.";
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> meetingService.updateMeeting("1", updateForMeeting));
        // THEN
        assertEquals(expected, exception.getMessage());
        verify(meetingRepository, never()).save(any(Meeting.class));
    }

    @Test
    void updateMeeting_ReturnsNoSuchElementException_NoMeetingId() {
        // GIVEN
        Meeting updateForMeeting = new Meeting(
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
        when(meetingRepository.existsById("1")).thenReturn(false);
        // THEN
        assertThrows(NoSuchElementException.class, () -> meetingService.updateMeeting("1", updateForMeeting));
        verify(meetingRepository, never()).save(any());
    }

    @Test
    void deleteMeeting_ReturnsDeletedMeeting() {
        // GIVEN
        Meeting existingMeeting = createMeeting();
        // WHEN
        when(meetingRepository.existsById(existingMeeting.id())).thenReturn(true);
        when(meetingRepository.findById(existingMeeting.id())).thenReturn(Optional.of(existingMeeting));
        Meeting deletedMeeting = meetingService.deleteMeeting(existingMeeting.id());
        // THEN
        assertNotNull(deletedMeeting);
        assertEquals(existingMeeting.id(), deletedMeeting.id());
        verify(meetingRepository, times(1)).deleteById(existingMeeting.id());
    }

    @Test
    void deleteMeeting_ReturnsNoSuchElementException() {
        // GIVEN
        String invalidId = "invalid";
        // WHEN
        when(meetingRepository.existsById(invalidId)).thenReturn(false);
        assertThrows(NoSuchElementException.class, () -> meetingService.deleteMeeting(invalidId));
        // THEN
        verify(meetingRepository, never()).deleteById(invalidId);
    }

    private static Meeting createMeeting() {
        return new Meeting(
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