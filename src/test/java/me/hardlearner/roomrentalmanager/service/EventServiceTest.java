package me.hardlearner.roomrentalmanager.service;

import me.hardlearner.roomrentalmanager.domain.Event;
import me.hardlearner.roomrentalmanager.domain.EventDummys;
import me.hardlearner.roomrentalmanager.domain.EventUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventServiceTest {
    @Test
    public void getEventsWithEmptyEvents() {
        List<Event> events = EventDummys.events;
        List<Event> emptyEvents = EventUtils.getEmptyEvents("190410", EventDummys.locations, events);
        List<Event> mergedEvents = Stream.of(events, emptyEvents)
                                            .flatMap(x -> x.stream())
                                            .collect(Collectors.toList());
//        System.out.println(Arrays.toString(mergedEvents.toArray()));
        Collections.sort(mergedEvents);
        for (Event event : mergedEvents) {
            System.out.println(event.getLocation().getRoomNo() + " : " + event.getLessorName() + " : " + event.getStartDateTime() + " ~ " + event.getEndDateTime());
        }
    }
}
