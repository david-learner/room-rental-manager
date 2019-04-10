package me.hardlearner.roomrentalmanager.domain;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventDummys {
    public static List<Location> locations = new ArrayList<>(Arrays.asList(
            new Location(401, PianoCategory.GRAND, 1),
            new Location(402, PianoCategory.UPRIGHT, 2),
            new Location(403, PianoCategory.GRAND, 1)
    ));

    public static List<Event> events = new ArrayList<>(Arrays.asList(
            new Event(locations.get(0), "황태원", LocalDateTime.of(2019, 4, 10, 9, 0), LocalDateTime.of(2019, 4, 10, 10, 0)),
            new Event(locations.get(0), "황태원", LocalDateTime.of(2019, 4, 10, 10, 0), LocalDateTime.of(2019, 4, 10, 11, 0)),
            new Event(locations.get(1), "김화원", LocalDateTime.of(2019, 4, 10, 10, 0), LocalDateTime.of(2019, 4, 10, 11, 0)),
            new Event(locations.get(1), "장설", LocalDateTime.of(2019, 4, 10, 11, 30), LocalDateTime.of(2019, 4, 10, 12, 30)),
            new Event(locations.get(2), "신동준", LocalDateTime.of(2019, 4, 10, 16, 0), LocalDateTime.of(2019, 4, 10, 18, 0))
    ));
}
