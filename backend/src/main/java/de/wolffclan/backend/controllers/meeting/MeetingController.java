package de.wolffclan.backend.controllers.meeting;

import de.wolffclan.backend.models.meeting.Meeting;
import de.wolffclan.backend.models.meeting.NewMeeting;
import de.wolffclan.backend.services.meeting.MeetingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/meetings")
public class MeetingController {
    private final MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllMeetings() {
        try {
            List<Meeting> getAllMeetings = meetingService.getAllMeetings();
            return ResponseEntity.ok(getAllMeetings);
        } catch (NoSuchElementException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception);
        }
    }

    @ResponseBody
    @PostMapping
    public ResponseEntity<Object> postMeeting(@RequestBody NewMeeting newMeeting) {
        try {
            Meeting saveMeeting = meetingService.saveMeeting(newMeeting);
            return ResponseEntity.ok(saveMeeting);
        } catch (NoSuchElementException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getMeetingById(@PathVariable String id) {
        try {
            Meeting searchMeeting = meetingService.getMeetingById(id);
            return ResponseEntity.ok(searchMeeting);
        } catch (NoSuchElementException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> putMeeting(@PathVariable String id, @RequestBody Meeting meeting) {
        try {
            Meeting saveMeeting = meetingService.updateMeeting(id, meeting);
            return ResponseEntity.ok(saveMeeting);
        } catch (NoSuchElementException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteMeeting(@PathVariable String id) {
        try {
            Meeting deletedMeeting = meetingService.deleteMeeting(id);
            return ResponseEntity.ok(deletedMeeting);
        } catch (NoSuchElementException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }
}
