package me.hardlearner.roomrentalmanager.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventUtils {
    public static Map<Location, List<Event>> listToMap(List<Event> events) {
        Map<Location, List<Event>> eventsMap = new HashMap<>();

        for (Event event : events) {
            if (eventsMap.get(event.getLocation()) == null) {
                eventsMap.put(event.getLocation(), new ArrayList<>());
            }
            if (eventsMap.get(event.getLocation()) != null) {
                eventsMap.get(event.getLocation()).add(event);
            }
        }

        return eventsMap;
    }
}
