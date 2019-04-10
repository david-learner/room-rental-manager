package me.hardlearner.roomrentalmanager.domain;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

public class EventUtilsTest {
    private List<Event> events = EventDummys.events;

    @Test
    public void getEmptyEvents() {
        String date = "190410";
        LocalDate todayDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyMMdd"));
        List<Event> emptyEvents = new ArrayList<>();
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(18, 0);
        LocalDateTime startDateTime = LocalDateTime.of(todayDate, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(todayDate, endTime);
        Event startBaseEvent = EmptyEvent.of(null, startDateTime, null);
        Event endBaseEvent = EmptyEvent.of(null, null, endDateTime);

        Map<Location, List<Event>> eventsMap = EventUtils.listToMap(events);
        for (Location location : eventsMap.keySet()) {
            List<Event> events = eventsMap.get(location);
            if (events.isEmpty()) {
                emptyEvents.add(EmptyEvent.of(location, startBaseEvent.getStartDateTime(), endBaseEvent.getEndDateTime()));
            }
            if (events.size() == 1) {
                for (Event event : events) {
                    if(!isStartDateTime(event)) {
                        emptyEvents.add(createEmptyEventOfStart(startBaseEvent, event));
                    }
                    if(!isEndDateTime(event)) {
                        emptyEvents.add(createEmptyEventOfEnd(endBaseEvent, event));
                    }
                }
            }
            if (events.size() > 1) {
                if (!isStartDateTime(events.get(0))) {
                    emptyEvents.add(createEmptyEventOfStart(startBaseEvent, events.get(0)));
                }
                for (int index = 0; index < events.size()-1; index++) {
                    if (!isEmptyDateTime(events.get(index), events.get(index + 1))) {
                        emptyEvents.add(createEmptyEvent(events.get(index), events.get(index+1)));
                    }
                }
                if (!isEndDateTime(events.get(events.size() - 1))) {
                    emptyEvents.add(createEmptyEventOfEnd(endBaseEvent, events.get(events.size()-1)));
                }
            }
        }

        for (Event emptyEvent : emptyEvents) {
            System.out.println(emptyEvent.getLocation().getRoomNo() + " : " + emptyEvent.getStartDateTime() + " ~ " + emptyEvent.getEndDateTime());
        }
    }

    public Event createEmptyEvent(Event previous, Event next) {
        return EmptyEvent.of(next.getLocation(), previous.getEndDateTime(), next.getStartDateTime());
    }

    public Event createEmptyEventOfStart(Event start, Event next) {
        return EmptyEvent.of(next.getLocation(), start.getStartDateTime(), next.getStartDateTime());
    }

    public Event createEmptyEventOfEnd(Event end, Event previous) {
        return EmptyEvent.of(previous.getLocation(), previous.getEndDateTime(), end.getEndDateTime());
    }

    public boolean isStartDateTime(Event event) {
        return event.getStartDateTime().toLocalTime().equals(LocalTime.of(9, 0));
    }

    public boolean isEndDateTime(Event event) {
        return event.getEndDateTime().toLocalTime().equals(LocalTime.of(18, 0));
    }

    // 이전 이벤트의 종료시간과 다음 이벤트의 시작시간의 간격이 1이상 이어야 한다
    public boolean isEmptyDateTime(Event previous, Event next) {
        return previous.getEndDateTime().isEqual(next.getStartDateTime());
    }

    @Test
    public void listToMap() {
        assertThat(EventUtils.listToMap(events)).isNotNull();

        Map<Location, List<Event>> eventsMap = EventUtils.listToMap(events);
        for (Location location : eventsMap.keySet()) {
            System.out.println("loc : " + location.getRoomNo() + " // " + Arrays.toString(eventsMap.get(location).toArray()));
        }
    }
}

class EmptyEvent extends Event {
    public static Event of(Location location, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Event event = new Event();
        event.setLocation(location);
        event.setLessorName("공실");
        event.setStartDateTime(startDateTime);
        event.setEndDateTime(endDateTime);
        return event;
    }
}
