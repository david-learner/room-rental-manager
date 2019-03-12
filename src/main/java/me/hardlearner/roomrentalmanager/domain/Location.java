package me.hardlearner.roomrentalmanager.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int roomNo;
    @Enumerated(EnumType.STRING)
    private PianoCategory pianoCategory;
    private int pianoCount;

    public Location() {
    }

    public Location(int roomNo, PianoCategory pianoCategory, int pianoCount) {
        this.roomNo = roomNo;
        this.pianoCategory = pianoCategory;
        this.pianoCount = pianoCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public PianoCategory getPianoCategory() {
        return pianoCategory;
    }

    public int getPianoCount() {
        return pianoCount;
    }

    public void setPianoCount(int pianoCount) {
        this.pianoCount = pianoCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return id == location.id &&
                roomNo == location.roomNo &&
                pianoCount == location.pianoCount &&
                pianoCategory == location.pianoCategory;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomNo, pianoCategory, pianoCount);
    }
}
