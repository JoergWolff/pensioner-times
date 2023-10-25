package de.wolffclan.backend.models.user;

import de.wolffclan.backend.models.hobby.Hobby;

import java.time.LocalDate;
import java.util.List;

public record NewUser(
        String firstName,
        String lastName,
        String email,
        LocalDate birthDay,
        List<Hobby> hobbies
) {
    public boolean isValid(){
        return (this.firstName != null)
                && (this.lastName != null)
                && (this.email != null)
                && (this.birthDay != null);
    }
}
