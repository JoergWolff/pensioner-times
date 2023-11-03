package de.wolffclan.backend.models.place;

public record NewPlace(
        String town,
        String postalCode,
        String street,
        String description
) {
    public boolean isValid(){
        return (this.town != null)
                && (this.postalCode != null)
                && (this.street != null);
    }
}
