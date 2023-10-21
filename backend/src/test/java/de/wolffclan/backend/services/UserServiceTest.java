package de.wolffclan.backend.services;

import de.wolffclan.backend.models.hobby.Hobby;
import de.wolffclan.backend.models.user.User;
import de.wolffclan.backend.repositories.UserRepository;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {
    UserRepository userRepository = mock(UserRepository.class);
    UserService userService = new UserService(userRepository);

    @Test
    void getAllUsers() {
        // GIVEN
        List<Hobby> hobbies = List.of(new Hobby("1", "Lesen", true, Instant.now(), Instant.now()));
        User newUser = new User("1", "Franz", "Hubermoser", "franz@huber.de", LocalDate.now(), hobbies, true, Instant.now(), Instant.now());
        List<User> expected = new ArrayList<>(List.of(newUser));
        // WHEN
        when(userRepository.findAll()).thenReturn(expected);
        List<User> actual = userService.getAllUsers();
        // THEN
        verify(userRepository).findAll();
        assertEquals(expected, actual);
    }
}