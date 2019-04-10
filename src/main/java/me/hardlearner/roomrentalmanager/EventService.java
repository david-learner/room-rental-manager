package me.hardlearner.roomrentalmanager;

import me.hardlearner.roomrentalmanager.domain.Event;
import me.hardlearner.roomrentalmanager.domain.EventEndDateTimeFirstComparator;
import me.hardlearner.roomrentalmanager.domain.EventInputDto;
import me.hardlearner.roomrentalmanager.domain.Location;
import me.hardlearner.roomrentalmanager.exception.EventNotFoundException;
import me.hardlearner.roomrentalmanager.repository.EventRepository;
import me.hardlearner.roomrentalmanager.repository.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class EventService {
    private static final Logger log = LoggerFactory.getLogger(EventService.class);

    @Autowired
    EventRepository eventRepository;

    @Autowired
    LocationRepository locationRepository;

    public Event getEvent(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException("event id : " + id + "is wrong"));
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<Event> getEventsWhereDate(String date) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyMMdd"));
        List<Event> events = eventRepository.findAllByStartDateTimeEquals(localDate.toString());
        Collections.sort(events);
        return events;
    }

    public Event createEvent(EventInputDto eventInputDto) {
        LocalDate date = eventInputDto.getStartdatetime().toLocalDate();
        int roomNo = eventInputDto.getRoomno();

        // 이벤트 날짜 && 장소 일치하는 애들 구해오기
        List<Event> events = eventRepository.findAllByStartDateTimeEqualsAndLocationEquals(date.toString(), roomNo);

        Event inputEvent = eventInputDto.toEvent(locationRepository.findByRoomNo(roomNo).orElseThrow(() -> new IllegalArgumentException("wrong room number")));
        // 이벤트들과 새롭게 만들어질 이벤트의 시간 겹치는지 확인
        if (inputEvent.isOverlap(events)) {
            throw new IllegalStateException("대여시간이 겹칩니다");
        }

        return eventRepository.save(inputEvent);
    }

    public Event updateEvent(EventInputDto eventInputDto, Long id) {
        Location dbLocation = locationRepository.findByRoomNo(eventInputDto.getRoomno()).orElseThrow(NullPointerException::new);
        Event dbEvent = eventRepository.findById(id).orElseThrow(EventNotFoundException::new);
        dbEvent.update(eventInputDto, dbLocation);
        List<Event> events = eventRepository.findAllByStartDateTimeEqualsAndLocationEquals(dbEvent.getStartDateTime().toLocalDate().toString(), dbLocation.getRoomNo());
        // 이벤트 리스트 들고와서 업데이트될 애 빼고 나머지 애들이랑 비교해서 겹치는지 확인
        for (Event event : events) {
            if (event.getId() == dbEvent.getId()) {
                events.remove(event);
                break;
            }
        }
        if (dbEvent.isOverlap(events)) {
            throw new IllegalStateException("대여시간이 겹칩니다");
        }
        return eventRepository.save(dbEvent);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    public List<Event> getEventsWhereDateOrderByEnddatetimeFirst(String date) {
        EventEndDateTimeFirstComparator enddateTimeFirst = new EventEndDateTimeFirstComparator();
        List<Event> events = getEventsWhereDate(date);
        Collections.sort(events, enddateTimeFirst);
        log.debug(Arrays.toString(events.toArray()));
        return events;
    }

    public List<Event> getEmptyTimesWhereDateOrderByStartDateTimeFirt(String date) {
        EventEndDateTimeFirstComparator startDateTimeComparator = new EventEndDateTimeFirstComparator();
        List<Event> events = getEventsWhereDate(date);
        Collections.sort(events, startDateTimeComparator);
        log.debug(Arrays.toString(events.toArray()));
        return events;
    }
}

