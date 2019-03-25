package me.hardlearner.roomrentalmanager.controller;

import me.hardlearner.roomrentalmanager.EventService;
import me.hardlearner.roomrentalmanager.domain.Event;
import me.hardlearner.roomrentalmanager.domain.EventInputDto;
import me.hardlearner.roomrentalmanager.domain.Location;
import me.hardlearner.roomrentalmanager.repository.EventRepository;
import me.hardlearner.roomrentalmanager.repository.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private static final Logger log = LoggerFactory.getLogger(EventController.class);

    @Autowired
    EventService eventService;

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEvent(id));
    }

    @GetMapping
    public ResponseEntity<List<Event>> getEvent() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/days/{day}")
    public ResponseEntity<List<Event>> getEventsOfDay(@PathVariable String date) {
        return ResponseEntity.ok(eventService.getEventsWhereDate(date));
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(EventInputDto eventInputDto) {
//        Location location = locationRepository.findByRoomNo(eventInputDto.getRoomno()).orElseThrow(IllegalArgumentException::new);
//        log.debug("eventInputDto start : " + eventInputDto.getStartdatetime());
//        Event event = eventRepository.save(eventInputDto.toEvent(location));
//        log.debug("event start : " + event.getStartDateTime());
        Event createdEvent = eventService.createEvent(eventInputDto);
        URI createdUri = linkTo(EventController.class).slash(createdEvent.getId()).toUri();
        return ResponseEntity.created(createdUri).body(createdEvent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(EventInputDto eventInputDto, @PathVariable Long id) {
        return ResponseEntity.ok(eventService.updateEvent(eventInputDto, id));
    }
}
