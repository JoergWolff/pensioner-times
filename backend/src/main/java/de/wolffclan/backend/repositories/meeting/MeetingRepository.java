package de.wolffclan.backend.repositories.meeting;

import de.wolffclan.backend.models.meeting.Meeting;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends MongoRepository<Meeting,String> {
}
