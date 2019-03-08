package me.hardlearner.roomrentalmanager.controller;

import me.hardlearner.roomrentalmanager.domain.Event;
import me.hardlearner.roomrentalmanager.domain.Location;
import me.hardlearner.roomrentalmanager.domain.PianoCategory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class EventController {
    @GetMapping
    public ResponseEntity<Event> getEvents() {
        Event event = new Event();
        event.setId(1L);
        event.setLocation(new Location(401, PianoCategory.GRAND, 1));
        event.setLessorName("황태원");
        return ResponseEntity.ok(event);
    }
}
