package de.wolffclan.backend.models.place;

public record Place(
        String id,
        String town,
        String postalCode,
        String street,
        String description
) {
}
