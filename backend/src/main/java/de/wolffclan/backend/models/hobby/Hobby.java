package de.wolffclan.backend.models.hobby;

import java.time.Instant;

public record Hobby(
        String id,
        String name,
        Boolean isActive,
        Instant createdAt,
        Instant updatedAt
) {
}
