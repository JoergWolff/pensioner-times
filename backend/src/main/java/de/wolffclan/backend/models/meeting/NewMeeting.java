package de.wolffclan.backend.models.meeting;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record NewMeeting(
        LocalDate meetingDay,
        LocalTime meetingTime,
        String description,
        int maxUser,
        int userCounter,
        List<String> userIds,
        String placeId
) {
    public boolean isValid() {
        return (this.meetingDay != null)
                && (this.meetingTime != null)
                && (this.maxUser >= 1)
                && (this.userCounter >= 1)
                && (this.userIds != null)
                && (this.placeId != null);
    }
}
