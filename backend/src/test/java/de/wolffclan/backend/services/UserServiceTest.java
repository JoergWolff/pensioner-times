package de.wolffclan.backend.services;

import de.wolffclan.backend.models.hobby.Hobby;
import de.wolffclan.backend.models.user.NewUser;
import de.wolffclan.backend.models.user.User;
import de.wolffclan.backend.repositories.UserRepository;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class UserServiceTest {
    static String birthDayString = "1987-12-22";
    static Instant instant = Instant.parse("2023-10-22T13:10:23.415Z");
    static LocalDate birthDay = createLocalDate(birthDayString);
    static List<Hobby> hobbies = createHobbies();

    UserRepository userRepository = mock(UserRepository.class);
    UserService userService = new UserService(userRepository);

    @Test
    void getAllUsers() {
        // GIVEN
        User user = createUser();
        List<User> expected = new ArrayList<>(List.of(user));
        // WHEN
        when(userRepository.findAll()).thenReturn(expected);
        List<User> actual = userService.getAllUsers();
        // THEN
        verify(userRepository).findAll();
        assertEquals(expected, actual);
    }

    @Test
    void saveUser_ReturnsUser() {
        // GIVEN
        NewUser newUser = new NewUser("Test", "TestTheBest", "test@testmail.test", birthDay, hobbies);
        when(userRepository.save(any(User.class))).thenReturn(
                new User("1",
                        newUser.firstName(),
                        newUser.lastName(),
                        newUser.email(),
                        newUser.birthDay(),
                        null,
                        true,
                        Instant.now(),
                        Instant.now()));
        // WHEN
        User savedUser = userService.saveUser(newUser);
        // THEN
        assertNotNull(savedUser);
        assertEquals(newUser.firstName(), savedUser.firstName());
        assertEquals(newUser.lastName(), savedUser.lastName());
        assertEquals(newUser.email(), savedUser.email());
        assertEquals(newUser.birthDay(), savedUser.birthDay());
        verify(userRepository, times(1)).save(any(User.class));

    }

    private static User createUser() {

        List<Hobby> hobbies = createHobbies();

        return new User(
                "1",
                "Test",
                "TestTheBest",
                "test@testmail.test",
                birthDay,
                hobbies,
                true,
                instant,
                instant);
    }

    private static List<Hobby> createHobbies() {
        List<Hobby> hobbies = new ArrayList<>();
        hobbies.add(new Hobby("1", "Test Hobby", true, instant, instant));
        return hobbies;
    }

    private static LocalDate createLocalDate(String birthDayAsString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(birthDayAsString, formatter);
    }
}