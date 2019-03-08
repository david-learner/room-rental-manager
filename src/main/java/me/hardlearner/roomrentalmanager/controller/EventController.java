package me.hardlearner.roomrentalmanager.controller;

import me.hardlearner.roomrentalmanager.domain.Event;
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
        return ResponseEntity.ok(event);
    }
}
