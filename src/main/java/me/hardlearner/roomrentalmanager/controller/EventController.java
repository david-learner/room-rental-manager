package me.hardlearner.roomrentalmanager.controller;

import me.hardlearner.roomrentalmanager.domain.Event;
import me.hardlearner.roomrentalmanager.domain.EventInputDto;
import me.hardlearner.roomrentalmanager.domain.Location;
import me.hardlearner.roomrentalmanager.domain.PianoCategory;
import me.hardlearner.roomrentalmanager.repository.EventRepository;
import me.hardlearner.roomrentalmanager.repository.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private static final Logger log =  LoggerFactory.getLogger(EventController.class);

    @Autowired
    EventRepository eventRepository;

    @Autowired
    LocationRepository locationRepository;

    @GetMapping
    public ResponseEntity<Event> getEvents() {
        Event event = new Event("황태원", null, null);
        event.setId(1L);
        event.setLocation(new Location(401, PianoCategory.GRAND, 1));
        return ResponseEntity.ok(event);
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
        Event event = eventRepository.save(eventInputDto.toEvent(location));
        URI createdUri = linkTo(EventController.class).slash(event.getId()).toUri();
        return ResponseEntity.created(createdUri).body(event);
    }
}
