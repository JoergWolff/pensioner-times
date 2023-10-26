package de.wolffclan.backend.models.hobby;

public record NewHobby(
        String name
) {
    public boolean isValid(){
        return this.name != null;
    }
}
