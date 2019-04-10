package me.hardlearner.roomrentalmanager.domain;

import java.util.Comparator;

public class EventEndDateTimeFirstComparator implements Comparator<Event> {
    @Override
    public int compare(Event first, Event second) {
        if (first.getEndDateTime().isBefore(second.getEndDateTime())) {
            return -1;
        }
        return 0;
    }
}
