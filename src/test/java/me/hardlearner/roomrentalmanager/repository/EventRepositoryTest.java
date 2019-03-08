package me.hardlearner.roomrentalmanager.repository;

import me.hardlearner.roomrentalmanager.domain.Event;
import me.hardlearner.roomrentalmanager.domain.Location;
import me.hardlearner.roomrentalmanager.domain.PianoCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class EventRepositoryTest {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    EventRepository eventRepository;

    @Test
    public void save_and_load() {
        int roomNo = 401;
        int pianoCount = 1;
        Location location = new Location(roomNo, PianoCategory.GRAND, pianoCount);
        Location savedLocation = locationRepository.save(location);

        Event event = new Event();
        String lessorName = "황태원";
        LocalDateTime startDateTime = LocalDateTime.of(2019, 3, 8, 9, 00);
        LocalDateTime endDateTime = LocalDateTime.of(2019, 3, 8, 11, 00);
        event.setLessorName(lessorName);
        event.setLocation(savedLocation);
        event.setStartDateTime(startDateTime);
        event.setEndDateTime(endDateTime);

        Event savedEvent = eventRepository.save(event);
        assertThat(savedEvent.getLocation()).isEqualTo(savedLocation);
        assertThat(savedEvent.getLessorName()).isEqualTo(lessorName);
        assertThat(savedEvent.getStartDateTime()).isEqualTo(startDateTime);
        assertThat(savedEvent.getEndDateTime()).isEqualTo(endDateTime);
    }
}