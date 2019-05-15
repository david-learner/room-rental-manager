package me.hardlearner.roomrentalmanager.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventUtils {
    private static final Logger log =  LoggerFactory.getLogger(EventUtils.class);

    public static Map<Location, List<Event>> listToMap(List<Location> locations, List<Event> events) {
        Map<Location, List<Event>> eventsMap = new HashMap<>();

        for (Location location : locations) {
            eventsMap.put(location, new ArrayList<>());
        }

        for (Event event : events) {
            eventsMap.get(event.getLocation()).add(event);
        }

//        for (Event event : events) {
//            if (eventsMap.get(event.getLocation()) == null) {
//                eventsMap.put(event.getLocation(), new ArrayList<>());
//            }
//            if (eventsMap.get(event.getLocation()) != null) {
//                eventsMap.get(event.getLocation()).add(event);
//            }
//        }

        return eventsMap;
    }

    public static List<Event> getEmptyEvents(String date, List<Location> locations, List<Event> dbEvents) {
        LocalDate today = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyMMdd"));
        List<Event> emptyEvents = new ArrayList<>();
        Event startBaseEvent = EmptyEvent.getStartEmptyEvent(today);
        Event endBaseEvent = EmptyEvent.getEndEmptyEvent(today);

        Map<Location, List<Event>> eventsMap = EventUtils.listToMap(locations, dbEvents);
        for (Location location : eventsMap.keySet()) {
            List<Event> events = eventsMap.get(location);
            if (events.isEmpty()) {
                emptyEvents.add(EmptyEvent.of(location, startBaseEvent.getStartDateTime(), endBaseEvent.getEndDateTime()));
                for (Event emptyEvent : emptyEvents) {
                    log.debug(emptyEvent.toString());
                }
            }
            if (events.size() == 1) {
                for (Event event : events) {
                    if (!isStartDateTime(event)) {
                        emptyEvents.add(createEmptyEventOfStart(startBaseEvent, event));
                    }
                    if (!isEndDateTime(event)) {
                        emptyEvents.add(createEmptyEventOfEnd(endBaseEvent, event));
                    }
                }
            }
            if (events.size() > 1) {
                if (!isStartDateTime(events.get(0))) {
                    emptyEvents.add(createEmptyEventOfStart(startBaseEvent, events.get(0)));
                }
                for (int index = 0; index < events.size() - 1; index++) {
                    if (!isEmptyDateTime(events.get(index), events.get(index + 1))) {
                        emptyEvents.add(createEmptyEvent(events.get(index), events.get(index + 1)));
                    }
                }
                if (!isEndDateTime(events.get(events.size() - 1))) {
                    emptyEvents.add(createEmptyEventOfEnd(endBaseEvent, events.get(events.size() - 1)));
                }
            }
        }
        return emptyEvents;
    }

    public static Event createEmptyEvent(Event previous, Event next) {
        return EmptyEvent.of(next.getLocation(), previous.getEndDateTime(), next.getStartDateTime());
    }

    public static Event createEmptyEventOfStart(Event start, Event next) {
        return EmptyEvent.of(next.getLocation(), start.getStartDateTime(), next.getStartDateTime());
    }

    public static Event createEmptyEventOfEnd(Event end, Event previous) {
        return EmptyEvent.of(previous.getLocation(), previous.getEndDateTime(), end.getEndDateTime());
    }

    public static boolean isStartDateTime(Event event) {
        return event.getStartDateTime().toLocalTime().equals(LocalTime.of(9, 0));
    }

    public static boolean isEndDateTime(Event event) {
        return event.getEndDateTime().toLocalTime().equals(LocalTime.of(18, 0));
    }

    // 이전 이벤트의 종료시간과 다음 이벤트의 시작시간의 간격이 1이상 이어야 한다
    public static boolean isEmptyDateTime(Event previous, Event next) {
        return previous.getEndDateTime().isEqual(next.getStartDateTime());
    }
}
