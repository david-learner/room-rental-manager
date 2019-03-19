package me.hardlearner.roomrentalmanager.domain;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class EventTest {

    private Location location401;
    private Location location402;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @Before
    public void setUp() {
        location401 = new Location(401, PianoCategory.GRAND, 1);
        location402 = new Location(402, PianoCategory.UPRIGHT, 2);
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
        LocalDateTime firstEventStartDateTime = LocalDateTime.of(2019, 3, 8, 9, 00);
        LocalDateTime firstEventEndDateTime = LocalDateTime.of(2019, 3, 8, 10, 00);
        LocalDateTime endDateTime = LocalDateTime.of(2019, 3, 8, 11, 00);
        LocalDateTime notOverlappedStartDateTime = LocalDateTime.of(2019, 3, 8, 10, 00);
        LocalDateTime overlappedStartDateTime = LocalDateTime.of(2019, 3, 8, 9, 59);

        Event firstEvent = new Event(location401, "황태원", firstEventStartDateTime, firstEventEndDateTime);
        Event notOverlappedEvent = new Event(location401, "김화원", notOverlappedStartDateTime, endDateTime);
        Event overlappedEvent = new Event(location401, "김화원", overlappedStartDateTime, endDateTime);

        assertThat(firstEvent.isOverlap(notOverlappedEvent)).isFalse();
        assertThat(firstEvent.isOverlap(overlappedEvent)).isTrue();
    }
}
