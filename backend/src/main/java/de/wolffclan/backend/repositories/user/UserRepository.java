package de.wolffclan.backend.repositories.user;

import de.wolffclan.backend.models.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByEmail(String email);
}
