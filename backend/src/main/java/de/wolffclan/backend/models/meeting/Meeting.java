package de.wolffclan.backend.models.meeting;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record Meeting(
        String id,
        LocalDate meetingDay,
        LocalTime meetingTime,
        String description,
        int maxUser,
        int userCounter,
        List<String> userIds,
        String placeId
) {
}
