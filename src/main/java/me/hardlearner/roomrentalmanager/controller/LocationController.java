package me.hardlearner.roomrentalmanager.controller;

import me.hardlearner.roomrentalmanager.domain.Location;
import me.hardlearner.roomrentalmanager.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    LocationRepository locationRepository;

    @GetMapping
    public ResponseEntity<List<Location>> getLocations() {
        // 매번 DB에서 꺼내올 필요가 없는 데이터
        List<Location> locations = locationRepository.findAll();
        return ResponseEntity.ok(locations);
    }
}
