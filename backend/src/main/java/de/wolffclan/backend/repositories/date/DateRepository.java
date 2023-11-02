package de.wolffclan.backend.repositories.date;

import de.wolffclan.backend.models.date.Date;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DateRepository extends MongoRepository<Date,String> {
}
