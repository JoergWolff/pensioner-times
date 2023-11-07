package de.wolffclan.backend.controllers.meeting;

import de.wolffclan.backend.models.hobby.Hobby;
import de.wolffclan.backend.models.meeting.Meeting;
import de.wolffclan.backend.models.user.User;
import de.wolffclan.backend.repositories.meeting.MeetingRepository;
import de.wolffclan.backend.repositories.place.PlaceRepository;
import de.wolffclan.backend.repositories.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MeetingControllerTest {

    String apiString = "/api/meetings";

    @Autowired
    MockMvc mockMvc;
    @Autowired
    MeetingRepository meetingRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PlaceRepository placeRepository;


    @Test
    void getAllMeetings_ReturnsEmptyArray() throws Exception {
        mockMvc.perform(get(apiString))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                []
                                """
                ));
    }

    @Test
    @DirtiesContext
    void getAllMeetings_ReturnsArrayOfMeetings() throws Exception {
        Meeting meeting = createTestMeeting();
        meetingRepository.save(meeting);

        mockMvc.perform(get(apiString))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                [{
                                "id": "123",
                                "meetingDay": "2024-02-23",
                                "meetingTime": "18:30:00",
                                "description": "TestMeeting",
                                "maxUser": 4,
                                "userCounter": 1,
                                "userIds": [
                                        "123"
                                        ],
                                "placeId": "123"
                                }]
                                """
                ));
    }

    @Test
    @DirtiesContext
    void postMeeting_ReturnsMeeting() throws Exception {
        // WHEN
        User user = createTestUser();
        userRepository.save(user);
        mockMvc.perform(MockMvcRequestBuilders.post(apiString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                          {
                                        "meetingDay": "2024-02-23",
                                        "meetingTime": "18:30:00",
                                        "description": "TestMeeting",
                                        "maxUser": 4,
                                        "userCounter": 1,
                                        "userIds": [
                                                "123"
                                                ],
                                        "placeId": "123"
                                        }
                                                """
                        ))
                // THEN
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                {
                                "meetingDay": "2024-02-23",
                                "meetingTime": "18:30:00",
                                "description": "TestMeeting",
                                "maxUser": 4,
                                "userCounter": 1,
                                "userIds": [
                                        "123"
                                        ],
                                "placeId": "123"
                                }
                                """
                ))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    void postMeeting_ReturnsUserNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(apiString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                          {
                                        "meetingDay": "2024-02-23",
                                        "meetingTime": "18:30:00",
                                        "description": "TestMeeting",
                                        "maxUser": 4,
                                        "userCounter": 1,
                                        "userIds": [
                                                "123"
                                                ],
                                        "placeId": "123"
                                        }
                                                """
                        ))
                // THEN
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User with id: 123 not found..."));
    }

    @Test
    @DirtiesContext
    void postMeeting_ReturnsTooFewInputs() throws Exception {
        // WHEN
        User user = createTestUser();
        userRepository.save(user);
        mockMvc.perform(MockMvcRequestBuilders.post(apiString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                          {
                                        "meetingDay": "2024-02-23",
                                        "description": "TestMeeting",
                                        "maxUser": 4,
                                        "userCounter": 1,
                                        "userIds": [
                                                "123"
                                                ],
                                        "placeId": "123"
                                        }
                                                """
                        ))
                // THEN
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Too few inputs..."));
    }

    @Test
    @DirtiesContext
    void getMeetingById_ReturnsOneMeeting() throws Exception {
        // GIVEN
        Meeting meeting = createTestMeeting();
        meetingRepository.save(meeting);
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get(apiString + "/" + meeting.id()))
                // THEN
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                {
                                "id": "123",
                                "meetingDay": "2024-02-23",
                                "meetingTime": "18:30:00",
                                "description": "TestMeeting",
                                "maxUser": 4,
                                "userCounter": 1,
                                "userIds": [
                                        "123"
                                        ],
                                "placeId": "123"
                                }
                                """
                ));
    }

    @Test
    @DirtiesContext
    void getMeetingById_ReturnsNotFound() throws Exception {
        // GIVEN
        Meeting meeting = createTestMeeting();
        meetingRepository.save(meeting);
        String falseId = "12345";
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get(apiString + "/" + falseId))
                // THEN
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Meeting with id: " + falseId + " not found..."));
    }

    @Test
    @DirtiesContext
    void putMeeting_ReturnsUpdatedMeeting() throws Exception {
        // GIVEN
        Meeting meeting = createTestMeeting();
        meetingRepository.save(meeting);
        User user = createTestUser();
        userRepository.save(user);
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get(apiString + "/" + meeting.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                            "meetingDay": "2024-02-23",
                                            "meetingTime": "18:30:00",
                                            "description": "TestMeetingUpdate",
                                            "maxUser": 4,
                                            "userCounter": 1,
                                            "userIds": [
                                                    "123"
                                                    ],
                                            "placeId": "123"
                                            }
                                        """
                        ))
                // THEN
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                {
                                    "meetingDay": "2024-02-23",
                                    "meetingTime": "18:30:00",
                                    "description": "TestMeeting",
                                    "maxUser": 4,
                                    "userCounter": 1,
                                    "userIds": [
                                            "123"
                                            ],
                                    "placeId": "123"
                                    }
                                """
                ));
    }

    @Test
    @DirtiesContext
    void putMeeting_ReturnsNoUserFound() throws Exception {
        // GIVEN
        Meeting meeting = createTestMeeting();
        meetingRepository.save(meeting);
        User user = createTestUser();
        userRepository.save(user);
        String falseId = "12345";
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get(apiString + "/" + falseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                            "meetingDay": "2024-02-23",
                                            "meetingTime": "18:30:00",
                                            "description": "TestMeetingUpdate",
                                            "maxUser": 4,
                                            "userCounter": 1,
                                            "userIds": [
                                                    "123"
                                                    ],
                                            "placeId": "123"
                                            }
                                        """
                        ))
                // THEN
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Meeting with id: 12345 not found..."));
    }

    @Test
    @DirtiesContext
    void deleteMeeting_ReturnsDeletedMeeting() throws Exception {
        // GIVEN
        Meeting deletingMeeting = createTestMeeting();
        meetingRepository.save(deletingMeeting);
        // THEN
        mockMvc.perform(MockMvcRequestBuilders.delete(apiString + "/delete/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("TestMeeting"));

    }

    @Test
    void deleteMeeting_ReturnsNoMeeting() throws  Exception {
        // THEN
        mockMvc.perform(MockMvcRequestBuilders.delete(apiString + "/delete/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Meeting id dosen't exists..."));
    }

    private static Meeting createTestMeeting() {
        return new Meeting(
                "123",
                LocalDate.of(2024, 2, 23),
                LocalTime.of(18, 30),
                "TestMeeting",
                4,
                1,
                List.of("123"),
                "123"
        );
    }

    private static User createTestUser() {

        LocalDate birthday = createTestBirthday();

        Instant instant = Instant.parse("2023-10-22T13:10:23.415Z");
        return new User(
                "123",
                "Test",
                "TestTheBest",
                "test@testmail.test",
                birthday,
                List.of(new Hobby("1", "Test HobbyModel", true, instant, instant)),
                true,
                instant,
                instant);
    }

    private static LocalDate createTestBirthday() {
        String birthDay = "2009-10-25";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(birthDay, formatter);
    }
}