package de.wolffclan.backend.controllers;

import de.wolffclan.backend.models.hobby.Hobby;
import de.wolffclan.backend.models.user.User;
import de.wolffclan.backend.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    String apiString = "/api/users";

    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;

    @Test
    void getAllUsers_ReturnsEmptyArray() throws Exception {

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
    void getAllUsers_ReturnsArrayOfUsers() throws Exception {
        // GIVEN
        User user = createTestUser();
        userRepository.save(user);

        mockMvc.perform(MockMvcRequestBuilders.get(apiString))
                // THEN
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                [{
                                "id": "1",
                                "firstName": "Test",
                                "lastName": "TestTheBest",
                                "email": "test@testmail.test",
                                "birthDay": "2009-10-25",
                                "hobbies": [
                                                {
                                                "id": "1",
                                                "name": "Test Hobby",
                                                "isActive": true,
                                                "createdAt": "2023-10-22T13:10:23.415Z",
                                                "updatedAt": "2023-10-22T13:10:23.415Z"
                                                 }
                                            ],
                                "isActive": true,
                                "createdAt": "2023-10-22T13:10:23.415Z",
                                "updatedAt": "2023-10-22T13:10:23.415Z"
                                }
                                ]
                                """
                ));
    }

    @Test
    @DirtiesContext
    void postUser_ReturnsUser() throws Exception {
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.post(apiString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                            "firstName": "Test",
                                             "lastName": "TestTheBest",
                                             "email": "test@testmail.test",
                                             "birthDay": "2009-10-25",
                                             "hobbies": [
                                                            {
                                                                "name": "Test Hobby"
                                                             }
                                                        ]
                                        }
                                        """
                        ))
                // THEN
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                {
                                    "firstName": "Test",
                                     "lastName": "TestTheBest",
                                     "email": "test@testmail.test",
                                     "birthDay": "2009-10-25",
                                     "hobbies": [
                                                    {
                                                        "name": "Test Hobby"
                                                     }
                                                ]
                                }
                                """))
                .andExpect(jsonPath("$.id").isNotEmpty());

    }

    @Test
    @DirtiesContext
    void postUser_ReturnsEmailExist() throws Exception {
        // GIVEN
        User user = createTestUser();
        userRepository.save(user);
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.post(apiString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                            "firstName": "Test1",
                                            "lastName": "TestTheBest1",
                                            "email": "test@testmail.test",
                                            "birthDay": "2022-02-12"
                                        }
                                        """
                        ))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Email exist..."));
    }

    @Test
    @DirtiesContext
    void postUser_ReturnsTooFewInputs() throws Exception {
        // GIVEN
        User user = createTestUser();
        userRepository.save(user);
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.post(apiString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                            "firstName": "Test1",
                                            "lastName": "TestTheBest1",
                                            "email": "test@testmail.test",
                                            "birth*******Day": "2022-02-12"
                                        }
                                        """
                        ))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Too few inputs..."));
    }

    @Test
    @DirtiesContext
    void getUserById_ReturnsOneUser() throws Exception {
        // GIVEN
        User user = createTestUser();
        userRepository.save(user);
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get(apiString + "/" + user.id()))
                // THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                            {
                                                "id": "1",
                                                 "firstName": "Test",
                                                 "lastName": "TestTheBest",
                                                 "email": "test@testmail.test",
                                                 "birthDay": "2009-10-25",
                                                 "hobbies": [
                                                                        {
                                                                        "id": "1",
                                                                        "name": "Test Hobby",
                                                                        "isActive": true,
                                                                        "createdAt": "2023-10-22T13:10:23.415Z",
                                                                        "updatedAt": "2023-10-22T13:10:23.415Z"
                                                                         }
                                                                    ],
                                                        "isActive": true,
                                                        "createdAt": "2023-10-22T13:10:23.415Z",
                                                        "updatedAt": "2023-10-22T13:10:23.415Z"
                                                        }
                        """));
    }

    @Test
    @DirtiesContext
    void getUserById_ReturnsNotFounded() throws Exception {
        // GIVEN
        User user = createTestUser();
        userRepository.save(user);
        String falseId = "1231";
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get(apiString + "/" + falseId))
                // THEN
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User with id: " + falseId + " not founded..."));
    }

    @Test
    @DirtiesContext
    void putUser_ReturnsUpdatedUser() throws Exception {
        // GIVEN
        User user = createTestUser();
        userRepository.save(user);
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.put(apiString + "/update/" + user.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                            "firstName": "TestUpdate",
                                             "lastName": "TestTheBestUpdate",
                                             "email": "updatetest@testmail.test",
                                             "birthDay": "2009-10-25",
                                             "hobbies": [
                                                            {
                                                                "name": "Test Hobby"
                                                             }
                                                        ]
                                        }
                                        """
                        ))
                // THEN
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                {
                                    "firstName": "TestUpdate",
                                     "lastName": "TestTheBestUpdate",
                                     "email": "updatetest@testmail.test",
                                     "birthDay": "2009-10-25",
                                     "hobbies": [
                                                    {
                                                        "name": "Test Hobby"
                                                     }
                                                ]
                                }
                                """))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    void putUser_ReturnsNoUser() throws Exception {
        // GIVEN
        User user = createTestUser();
        userRepository.save(user);
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.put(apiString + "/update/d9b9-39ab-46a9-8fa5-19a5e931")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                            "firstName": "TestUpdate",
                                             "lastName": "TestTheBestUpdate",
                                             "email": "updatetest@testmail.test",
                                             "birthDay": "2009-10-25",
                                             "hobbies": [
                                                            {
                                                                "name": "Test Hobby"
                                                             }
                                                        ]
                                        }
                                        """
                        ))
                // THEN
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User id dosen't exists..."));
    }

    private static User createTestUser() {

        LocalDate birthday = createTestBirthday();

        Instant instant = Instant.parse("2023-10-22T13:10:23.415Z");
        return new User(
                "1",
                "Test",
                "TestTheBest",
                "test@testmail.test",
                birthday,
                List.of(new Hobby("1", "Test Hobby", true, instant, instant)),
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