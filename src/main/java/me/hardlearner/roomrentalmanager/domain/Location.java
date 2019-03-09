package me.hardlearner.roomrentalmanager.domain;

import javax.persistence.*;

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
}
