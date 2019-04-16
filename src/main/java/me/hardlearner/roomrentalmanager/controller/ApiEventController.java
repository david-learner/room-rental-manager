package me.hardlearner.roomrentalmanager.controller;

import me.hardlearner.roomrentalmanager.EventService;
import me.hardlearner.roomrentalmanager.domain.Event;
import me.hardlearner.roomrentalmanager.domain.EventInputDto;
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
public class ApiEventController {
    private static final Logger log = LoggerFactory.getLogger(ApiEventController.class);

    @Autowired
    EventService eventService;

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEvent(id));
    }

    @GetMapping
    public ResponseEntity<List<Event>> getEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/days/{date}")
    public ResponseEntity<List<Event>> getEventsOfDay(@PathVariable String date) {
        return ResponseEntity.ok(eventService.getEventsWhereDate(date));
    }

    @GetMapping("/days/{date}/all")
    public ResponseEntity<List<Event>> getEventsAndEmptyEventsOfDay(@PathVariable String date) {
        return ResponseEntity.ok(eventService.getEventsAndEmptyEventsWhereDate(date));
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(EventInputDto eventInputDto) {
        Event createdEvent = eventService.createEvent(eventInputDto);
        URI createdUri = linkTo(ApiEventController.class).slash(createdEvent.getId()).toUri();
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

    @GetMapping("/days/{date}/endfirst")
    public ResponseEntity<List<Event>> getEventsOfDayOrderByEndDatetime(@PathVariable String date) {
        return ResponseEntity.ok(eventService.getEventsWhereDateOrderByEnddatetimeFirst(date));
    }

    @GetMapping("/days/{date}/emptyevents")
    public ResponseEntity<List<Event>> getEmptyEventsOfDayOrderByStartDateTime(@PathVariable String date) {
        return ResponseEntity.ok(eventService.getEmptyTimesWhereDateOrderByStartDateTimeFirt(date));
    }
}
