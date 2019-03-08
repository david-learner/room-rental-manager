package me.hardlearner.roomrentalmanager.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int roomNo;
    private Enum<PianoCategory> pianoCategory;
    private int pianoCount;

    public Location(int roomNo, Enum pianoCategory, int pianoCount) {
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

    public Enum getPianoCategory() {
        return pianoCategory;
    }

    public void setPianoCategory(Enum pianoCategory) {
        this.pianoCategory = pianoCategory;
    }

    public int getPianoCount() {
        return pianoCount;
    }

    public void setPianoCount(int pianoCount) {
        this.pianoCount = pianoCount;
    }
}
