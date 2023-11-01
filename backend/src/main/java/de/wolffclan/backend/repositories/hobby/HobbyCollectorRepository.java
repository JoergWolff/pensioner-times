package de.wolffclan.backend.repositories.hobby;

import de.wolffclan.backend.models.hobby.HobbyCollector;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HobbyCollectorRepository extends MongoRepository<HobbyCollector, String> {
    boolean existsByName(String name);

    HobbyCollector getByName(String name);
}
