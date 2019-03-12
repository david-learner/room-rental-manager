package me.hardlearner.roomrentalmanager.domain;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class EventTest {
    @Test
    public void update() {
        Location location = new Location(401, PianoCategory.GRAND, 1);
        Location updateLocation = new Location(402, PianoCategory.UPRIGHT, 2);

        LocalDateTime startDateTime = LocalDateTime.of(2019, 3, 8, 9, 00);
        LocalDateTime endDateTime = LocalDateTime.of(2019, 3, 8, 11, 00);
        LocalDateTime updateStartDateTime = LocalDateTime.of(2019, 3, 8, 10, 30);

        EventInputDto dto = new EventInputDto();
        dto.setRoomno(402);
        dto.setLessorname("김화원");
        dto.setStartdatetime(updateStartDateTime);
        dto.setEnddatetime(endDateTime);

        Event event = new Event(location, "황태원", startDateTime, endDateTime);
        event.update(dto, updateLocation);

        assertThat(event.getLocation()).isEqualTo(updateLocation);
        assertThat(event.getLessorName()).isEqualTo("김화원");
        assertThat(event.getStartDateTime()).isEqualTo(updateStartDateTime);
    }
}
