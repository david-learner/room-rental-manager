package me.hardlearner.roomrentalmanager.domain;

import java.util.Comparator;

public class EventStartDateTimeFirstComparator implements Comparator<Event> {
    @Override
    public int compare(Event first, Event second) {
        if (first.getStartDateTime().isBefore(second.getStartDateTime())) {
            return -1;
        }
        return 0;
    }
}
