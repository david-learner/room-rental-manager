package me.hardlearner.roomrentalmanager.controller;

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
        event.setId(7L);
        event.setRoomNo("401");
        event.setLessorName("FloralRoom");
        return ResponseEntity.ok(event);
    }
}
