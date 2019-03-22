package me.hardlearner.roomrentalmanager.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventInputDto {
    private int roomno;
    private String lessorname;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startdatetime;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime enddatetime;

    public EventInputDto() {
    }

    public int getRoomno() {
        return roomno;
    }

    public void setRoomno(int roomno) {
        this.roomno = roomno;
    }

    public String getLessorname() {
        return lessorname;
    }

    public void setLessorname(String lessorname) {
        this.lessorname = lessorname;
    }

    public LocalDateTime getStartdatetime() {
        return startdatetime;
    }

    public void setStartdatetime(LocalDateTime startdatetime) {
//        System.out.println("startDate is converting");
//        this.startdatetime = LocalDateTime.parse(startdatetime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));;
        this.startdatetime = startdatetime;
    }

    public LocalDateTime getEnddatetime() {
        return enddatetime;
    }

    public void setEnddatetime(LocalDateTime enddatetime) {
        this.enddatetime = enddatetime;
    }

    public Event toEvent(Location location) {
        return new Event(location, lessorname, startdatetime, enddatetime);
    }

    @Override
    public String toString() {
        return "EventInputDto{" +
                "roomno=" + roomno +
                ", lessorname='" + lessorname + '\'' +
                ", startdatetime=" + startdatetime +
                ", enddatetime=" + enddatetime +
                '}';
    }
}
