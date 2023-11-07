package de.wolffclan.backend.services.meeting;

import de.wolffclan.backend.models.meeting.Meeting;
import de.wolffclan.backend.models.meeting.NewMeeting;
import de.wolffclan.backend.repositories.meeting.MeetingRepository;
import de.wolffclan.backend.repositories.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;


    public MeetingService(MeetingRepository meetingRepository, UserRepository userRepository) {
        this.meetingRepository = meetingRepository;
        this.userRepository = userRepository;
    }

    public List<Meeting> getAllMeetings(){return meetingRepository.findAll();}

    public Meeting saveMeeting(NewMeeting newMeeting) {
        if (newMeeting.isValid()) {
            String userId = newMeeting.userIds().get(0);
            if (existsUserById(userId)) {
                Meeting savedMeeting = new Meeting(
                        UUID.randomUUID().toString(),
                        newMeeting.meetingDay(),
                        newMeeting.meetingTime(),
                        newMeeting.description(),
                        newMeeting.maxUser(),
                        1,
                        newMeeting.userIds(),
                        newMeeting.placeId()
                );
                return meetingRepository.save(savedMeeting);

            }
            throw new NoSuchElementException("User with id: " + userId + " not found...");
        }
        throw new NoSuchElementException("Too few inputs...");
    }

    public Meeting getMeetingById(String id) {
        return meetingRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Meeting with id: " + id + " not found..."));
    }

    public Meeting updateMeeting(String id, Meeting meeting) {
        if (existMeetingById(id)) {
            Meeting existMeeting = getMeetingById(id);
            List<String> userIds = meeting.userIds().stream()
                    .filter(this::existsUserById)
                    .toList();
            int counter = userIds.size();
            if (counter <= meeting.maxUser()) {
                Meeting savingMeeting = new Meeting(
                        existMeeting.id(),
                        meeting.meetingDay(),
                        meeting.meetingTime(),
                        meeting.description(),
                        meeting.maxUser(),
                        counter,
                        userIds,
                        meeting.placeId()
                );
                meetingRepository.save(savingMeeting);
                return savingMeeting;
            }
            throw new NoSuchElementException("Too much Users. Maximize the Users to " + counter + ".");

        }
        throw new NoSuchElementException("Meeting id dosen't exists...");
    }

    public Meeting deleteMeeting(String id) {
        if (existMeetingById(id)) {
            Meeting deletedMeeting = getMeetingById(id);
            meetingRepository.deleteById(id);
            return deletedMeeting;
        }
        throw new NoSuchElementException("Meeting id dosen't exists...");
    }

    private boolean existMeetingById(String id) {
        return meetingRepository.existsById(id);
    }

    private boolean existsUserById(String id) {
        return userRepository.existsById(id);
    }
}
