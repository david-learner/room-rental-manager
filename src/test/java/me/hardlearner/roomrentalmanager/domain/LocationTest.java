package me.hardlearner.roomrentalmanager.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class LocationTest {
    @Test
    public void create() {
        int roomNo = 401;
        int pianoCount = 1;
        Location location = new Location(roomNo, PianoCategory.GRAND, pianoCount);

        assertThat(location.getRoomNo()).isEqualTo(401);
        assertThat(location.getPianoCategory()).isEqualTo(PianoCategory.GRAND);
        assertThat(location.getPianoCount()).isEqualTo(1);
    }
}