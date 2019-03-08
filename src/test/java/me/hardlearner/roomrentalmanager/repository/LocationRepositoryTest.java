package me.hardlearner.roomrentalmanager.repository;

import me.hardlearner.roomrentalmanager.domain.Location;
import me.hardlearner.roomrentalmanager.domain.PianoCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class LocationRepositoryTest {

    @Autowired
    LocationRepository locationRepository;

    @Test
    public void save_and_load() {
        int roomNo = 401;
        int pianoCount = 1;
        Location location = new Location(roomNo, PianoCategory.GRAND, pianoCount);

        Location savedLocation = locationRepository.save(location);

        assertThat(savedLocation.getRoomNo()).isEqualTo(roomNo);
        assertThat(savedLocation.getPianoCategory()).isEqualTo(PianoCategory.GRAND);
        assertThat(savedLocation.getPianoCount()).isEqualTo(pianoCount);
    }
}
