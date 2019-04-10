package me.hardlearner.roomrentalmanager.domain;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class EventEndDateTimeFirstComparatorTest {
    @Test
    public void comparator() {
        EventEndDateTimeFirstComparator comparator = new EventEndDateTimeFirstComparator();
        Location location401 = new Location(401, PianoCategory.UPRIGHT, 2);
        Location location402 = new Location(402, PianoCategory.UPRIGHT, 2);
        Event e1 = new Event(location401, "황태원", LocalDateTime.of(2019, 1, 1, 10, 10), LocalDateTime.of(2019, 1, 1, 10, 40));
        Event e2 = new Event(location402, "김화원", LocalDateTime.of(2019, 1, 1, 10, 10), LocalDateTime.of(2019, 1, 1, 10, 30));

        List<Event> events = new ArrayList<>();
        events.add(e1);
        events.add(e2);
        Collections.sort(events, comparator);
        assertThat(events.get(0)).isEqualTo(e2);
    }
}
