package me.hardlearner.roomrentalmanager.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class EmptyEvent extends Event {
    public static Event of(Location location, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Event event = new Event();
        event.setLocation(location);
        event.setLessorName("공실");
        event.setStartDateTime(startDateTime);
        event.setEndDateTime(endDateTime);
        return event;
    }

    public static Event getStartEmptyEvent(LocalDate today) {
        LocalTime startTime = LocalTime.of(9, 0);
        LocalDateTime startDateTime = LocalDateTime.of(today, startTime);
        return EmptyEvent.of(null, startDateTime, null);
    }

    public static Event getEndEmptyEvent(LocalDate today) {
        LocalTime endTime = LocalTime.of(18, 0);
        LocalDateTime endDateTime = LocalDateTime.of(today, endTime);
        return EmptyEvent.of(null, null, endDateTime);
    }
}