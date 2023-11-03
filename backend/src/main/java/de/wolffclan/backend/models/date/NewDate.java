package de.wolffclan.backend.models.date;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record NewDate(
        LocalDate meetingDay,
        LocalTime meetingTime,
        String description,
        int maxUser,
        int userCounter,
        List<String> userIds,
        String placeId
) {
}
