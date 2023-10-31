package de.wolffclan.backend.services;

import de.wolffclan.backend.models.hobby.Hobby;
import de.wolffclan.backend.models.user.NewUser;
import de.wolffclan.backend.models.user.User;
import de.wolffclan.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(NewUser newUser) {
        List<Hobby> hobbyList = new ArrayList<>();
        if (newUser.isValid()) {
            if (!existEmail(newUser.email())) {
                if (newUser.hobbies() != null) {
                    for (Hobby hobby : newUser.hobbies()) {
                        hobbyList.add(
                                new Hobby(
                                        UUID.randomUUID().toString(),
                                        hobby.name(),
                                        true,
                                        Instant.now(),
                                        Instant.now()
                                )
                        );
                    }
                }
                User saveUser = new User(
                        UUID.randomUUID().toString(),
                        newUser.firstName(),
                        newUser.lastName(),
                        newUser.email(),
                        newUser.birthDay(),
                        hobbyList,
                        true,
                        Instant.now(),
                        Instant.now());

                return userRepository.save(saveUser);
            } else {
                throw new NoSuchElementException("Email exist...");
            }
        }
        throw new NoSuchElementException("Too few inputs...");
    }

    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with id: " + id + " not founded..."));
    }

    public User updateUser(String userId, User user) {
        if (existUserById(userId)) {
            User existUser = getUserById(userId);
            List<Hobby> newHobbies = user.hobbies().stream()
                                        .map(this::updateHobby)
                                        .collect(Collectors.toMap(Hobby::name,hobby -> hobby,(existing, replacement)->existing))
                                        .values().stream().toList();

            User savingUser = new User(
                    existUser.id(),
                    user.firstName(),
                    user.lastName(),
                    user.email(),
                    user.birthDay(),
                    newHobbies,
                    user.isActive(),
                    user.createdAt(),
                    Instant.now()
            );
            userRepository.save(savingUser);

            return savingUser;
        }
        throw new NoSuchElementException("User id dosen't exists...");
    }

    private Hobby updateHobby(Hobby hobby) {
        if (hobby.id() != null) {
            return new Hobby(
                    hobby.id(),
                    hobby.name(),
                    hobby.isActive(),
                    hobby.createdAt(),
                    Instant.now()
            );
        }
        return new Hobby(
                UUID.randomUUID().toString(),
                hobby.name(),
                true,
                Instant.now(),
                Instant.now()
        );
    }

    private boolean existEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private boolean existUserById(String id) {
        return userRepository.existsById(id);
    }
}
