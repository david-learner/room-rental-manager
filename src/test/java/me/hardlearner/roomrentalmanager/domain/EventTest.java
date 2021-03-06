package me.hardlearner.roomrentalmanager.domain;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class EventTest {

    private Location location401;
    private Location location402;
    private Location location403;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @Before
    public void setUp() {
        location401 = new Location(401, PianoCategory.GRAND, 1);
        location402 = new Location(402, PianoCategory.UPRIGHT, 2);
        location403 = new Location(403, PianoCategory.UPRIGHT, 2);
        startDateTime = LocalDateTime.of(2019, 3, 8, 9, 00);
        endDateTime = LocalDateTime.of(2019, 3, 8, 11, 00);
    }

    @Test
    public void create_pass() {
        Event normalEvent = new Event(location401, "황태원", startDateTime, endDateTime);
        assertThat(normalEvent).isNotNull();
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_fail() {
        Event abnormalEvent = new Event(location401, "황태원", endDateTime, startDateTime);
    }

    @Test
    public void update() {
        LocalDateTime updateStartDateTime = LocalDateTime.of(2019, 3, 8, 10, 30);

        EventInputDto dto = new EventInputDto();
        dto.setRoomno(402);
        dto.setLessorname("김화원");
        dto.setStartdatetime(updateStartDateTime);
        dto.setEnddatetime(endDateTime);

        Event event = new Event(location401, "황태원", startDateTime, endDateTime);
        event.update(dto, location402);

        assertThat(event.getLocation()).isEqualTo(location402);
        assertThat(event.getLessorName()).isEqualTo("김화원");
        assertThat(event.getStartDateTime()).isEqualTo(updateStartDateTime);
    }

    @Test
    public void isOverlap() {
        LocalDateTime previousEventStartDateTime = LocalDateTime.of(2019, 3, 8, 10, 00);
        LocalDateTime previousEventEndDateTime = LocalDateTime.of(2019, 3, 8, 12, 00);
        LocalDateTime addEventStartDateTime = LocalDateTime.of(2019, 3, 8, 12, 00);
        LocalDateTime addEventEndDateTime = LocalDateTime.of(2019, 3, 8, 14, 00);
        LocalDateTime nextEventStartDateTime = LocalDateTime.of(2019, 3, 8, 14, 00);
        LocalDateTime nextEventEndDateTime = LocalDateTime.of(2019, 3, 8, 16, 00);
        LocalDateTime nextEventOverlapStartDateTime = LocalDateTime.of(2019, 3, 8, 13, 00);
        LocalDateTime nextEventOverlapEndDateTime = LocalDateTime.of(2019, 3, 8, 15, 00);

        Event previousEvent = new Event(location401, "황태원", previousEventStartDateTime, previousEventEndDateTime);
        Event addEvent = new Event(location401, "김화원", addEventStartDateTime, addEventEndDateTime);
        Event nextEvent = new Event(location401, "김화원", nextEventStartDateTime, nextEventEndDateTime);
        Event nextOverlapEvent = new Event(location401, "김화원", nextEventOverlapStartDateTime, nextEventOverlapEndDateTime);

        List<Event> events = new ArrayList<>();
        events.add(nextEvent);
        events.add(previousEvent);
        assertThat(addEvent.isOverlap(events)).isFalse();

        events.clear();
        events.add(nextOverlapEvent);
        events.add(previousEvent);
        assertThat(addEvent.isOverlap(events)).isTrue();
    }

    @Test
    public void checkIn() {
        Event eventCheckedIn = new Event(location401, "황태원", startDateTime, endDateTime);
        eventCheckedIn.checkIn();
        assertThat(eventCheckedIn.isCheckIn()).isTrue();
        eventCheckedIn.cancelCheckIn();
        assertThat(eventCheckedIn.isCheckIn()).isFalse();
    }

    @Test
    public void sort() {
        Event e1 = new Event(location401, "황태원1", LocalDateTime.of(2019, 04, 07, 10, 00), LocalDateTime.of(2019, 04, 07, 11, 00));
        Event e2 = new Event(location402, "황태원2", LocalDateTime.of(2019, 04, 07, 10, 30), LocalDateTime.of(2019, 04, 07, 11, 30));
        Event e3 = new Event(location401, "황태원3", LocalDateTime.of(2019, 04, 07, 11, 00), LocalDateTime.of(2019, 04, 07, 12, 00));

        List<Event> events = new ArrayList<>();
        events.add(e1);
        events.add(e2);
        events.add(e3);
        Collections.sort(events);
        System.out.println(Arrays.toString(events.toArray()));
    }
}
