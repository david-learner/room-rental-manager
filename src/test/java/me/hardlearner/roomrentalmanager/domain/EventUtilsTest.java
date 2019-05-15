package me.hardlearner.roomrentalmanager.domain;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class EventUtilsTest {
    private List<Event> events = EventDummys.events;

    @Test
    public void getEmptyEvents() {
//        List<Event> emptyEvents = EventUtils.getEmptyEvents("190410", events);
        List<Event> emptyEvents = EventUtils.getEmptyEvents("190410", EventDummys.locations, EventDummys.emptyEvents);

//        for (Event emptyEvent : emptyEvents) {
//            System.out.println(emptyEvent.getLocation().getRoomNo() + " : " + emptyEvent.getStartDateTime() + " ~ " + emptyEvent.getEndDateTime());
//        }
    }

//    @Test
//    public void listToMap() {
//        assertThat(EventUtils.listToMap(events)).isNotNull();
//
//        Map<Location, List<Event>> eventsMap = EventUtils.listToMap(events);
//        for (Location location : eventsMap.keySet()) {
//            System.out.println("loc : " + location.getRoomNo() + " // " + Arrays.toString(eventsMap.get(location).toArray()));
//        }
//    }
}
