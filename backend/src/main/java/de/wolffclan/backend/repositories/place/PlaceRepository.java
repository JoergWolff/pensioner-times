package de.wolffclan.backend.repositories.place;

import de.wolffclan.backend.models.place.Place;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends MongoRepository<Place,String> {
}
