package de.wolffclan.backend.models.user;

import de.wolffclan.backend.models.hobby.Hobby;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public record User(
        String id,
        String firstName,
        String lastName,
        String email,
        LocalDate birthDay,
        List<Hobby> hobbies,
        Boolean isActive,
        Instant createdAt,
        Instant updatedAt
) {
}
