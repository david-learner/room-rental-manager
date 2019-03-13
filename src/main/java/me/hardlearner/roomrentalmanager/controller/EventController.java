package me.hardlearner.roomrentalmanager.controller;

import me.hardlearner.roomrentalmanager.domain.Event;
import me.hardlearner.roomrentalmanager.domain.EventInputDto;
import me.hardlearner.roomrentalmanager.domain.Location;
import me.hardlearner.roomrentalmanager.domain.PianoCategory;
import me.hardlearner.roomrentalmanager.exception.EventNotFoundException;
import me.hardlearner.roomrentalmanager.repository.EventRepository;
import me.hardlearner.roomrentalmanager.repository.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private static final Logger log = LoggerFactory.getLogger(EventController.class);

    @Autowired
    EventRepository eventRepository;

    @Autowired
    LocationRepository locationRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEvents(@PathVariable Long id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException("event id : " + id + "is wrong"));
        log.debug("event : " + event.toString());
        return ResponseEntity.ok(event);
    }

    @GetMapping
    public ResponseEntity<List<Event>> getEvents() {
        List<Event> events = eventRepository.findAll();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/days/{day}")
    public ResponseEntity<List<Event>> getEventsOfDay(@PathVariable String day) {
        LocalDate localDate = LocalDate.parse(day, DateTimeFormatter.ofPattern("yyMMdd"));
        List<Event> events = eventRepository.findAllByStartDateTimeEquals(localDate.toString());
        return ResponseEntity.ok(events);
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(EventInputDto eventInputDto) {
        Location location = locationRepository.findByRoomNo(eventInputDto.getRoomno()).orElseThrow(IllegalArgumentException::new);
        log.debug("eventInputDto start : " + eventInputDto.getStartdatetime());
        Event event = eventRepository.save(eventInputDto.toEvent(location));
        log.debug("event start : " + event.getStartDateTime());
        URI createdUri = linkTo(EventController.class).slash(event.getId()).toUri();
        return ResponseEntity.created(createdUri).body(event);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventRepository.deleteById(id);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(EventInputDto eventInputDto, @PathVariable Long id) {
        log.debug("update api called");
        Location dbLocation = locationRepository.findByRoomNo(eventInputDto.getRoomno()).orElseThrow(NullPointerException::new);
        Event dbEvent = eventRepository.findById(id).orElseThrow(EventNotFoundException::new);
        dbEvent.update(eventInputDto, dbLocation);
        Event updatedEvent = eventRepository.save(dbEvent);
        return ResponseEntity.ok(updatedEvent);
    }
}
