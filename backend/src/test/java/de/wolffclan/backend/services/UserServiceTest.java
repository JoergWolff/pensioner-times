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
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void saveUser_ReturnsEmailExist() {
        // GIVEN
        NewUser newUser = new NewUser("Test", "TestTheBest", "test@testmail.test", birthDay, hobbies);

        // WHEN
        when(userRepository.existsByEmail(newUser.email())).thenReturn(true);
        assertThrows(NoSuchElementException.class, () -> userService.saveUser(newUser));

        // THEN
        verify(userRepository, times(1)).existsByEmail(newUser.email());
    }

    @Test
    void saveUser_ReturnsTooFewInputs() {
        // GIVEN
        NewUser newUser = new NewUser(null, "TestTheBest", "test@testmail.test", birthDay, hobbies);

        // WHEN
        assertThrows(NoSuchElementException.class, () -> userService.saveUser(newUser));

        // THEN
        verify(userRepository, never()).save(any(User.class));
        verify(userRepository, never()).existsByEmail(anyString());
    }

    @Test
    void getUserById_ReturnsValidUser() {
        // GIVEN
        String userId = "123";
        User searchUser = new User(userId, "Test", "TestTheBest", "test@testmail.test", LocalDate.now(), null, true, Instant.now(), Instant.now());
        when(userRepository.findById(userId)).thenReturn(Optional.of(searchUser));

        // WHEN
        User user = userService.getUserById(userId);

        // THEN
        assertNotNull(user);
        assertEquals(userId, user.id());
        assertEquals(searchUser.firstName(), user.firstName());
        assertEquals(searchUser.lastName(), user.lastName());
        assertEquals(searchUser.email(), user.email());

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void getUserById_ReturnsInvalidId() {

        // GIVEN
        String invalidId = "invalid";
        when(userRepository.findById(invalidId)).thenReturn(java.util.Optional.empty());

        // WHEN
        assertThrows(NoSuchElementException.class, () -> userService.getUserById(invalidId));

        // THEN
        verify(userRepository, times(1)).findById(invalidId);
    }

    @Test
    void updateUser_ReturnsUpdatedUser() {

        // GIVEN
        User existingUser = createUser();
        User updateForUser = new User("1","TestUpdate", "TestTheBestUpdate", "test@testmail.test", birthDay, hobbies,true, Instant.now(),Instant.now());
        when(userRepository.existsById("1")).thenReturn(true);
        when(userRepository.findById("1")).thenReturn(Optional.of(existingUser));
        // WHEN
        User updatedUser = userService.updateUser("1",updateForUser);
        // THEN
        assertNotNull(updatedUser);
        assertEquals(updateForUser.firstName(), updatedUser.firstName());
        assertEquals(updateForUser.lastName(), updatedUser.lastName());
        assertEquals(updateForUser.email(), updatedUser.email());
        assertEquals(updateForUser.birthDay(), updatedUser.birthDay());
        verify(userRepository, times(1)).save(any(User.class));

    }

    @Test
    void updateUser_ReturnsNoSuchElementException() {

        // GIVEN
        User updateForUser = new User("1","TestUpdate", "TestTheBestUpdate", "test@testmail.test", birthDay, hobbies,true, Instant.now(),Instant.now());
        when(userRepository.existsById("1")).thenReturn(false);
        // WHEN
        assertThrows(NoSuchElementException.class, () -> userService.updateUser("1", updateForUser));
        // THEN
        verify(userRepository, never()).save(any());

    }

    @Test
    void deleteUser_ReturnsDeletedUser() {
        // GIVEN
        User existingUser = createUser();
        when(userRepository.existsById("1")).thenReturn(true);
        when(userRepository.findById("1")).thenReturn(Optional.of(existingUser));
        // WHEN
        User deletedUser = userService.deleteUser("1");
        // THEN
        assertNotNull(deletedUser);
        assertEquals("1", deletedUser.id());
        assertEquals(existingUser.firstName(), deletedUser.firstName());

        verify(userRepository, times(1)).deleteById("1");
    }

    @Test
    void deleteUser_ReturnsNoSuchElementException() {
        // GIVEN
        when(userRepository.existsById("1")).thenReturn(false);
        // WHEN
        assertThrows(NoSuchElementException.class, () -> userService.deleteUser("1"));
        // THEN
        verify(userRepository, never()).deleteById(any());
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