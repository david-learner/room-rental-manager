package me.hardlearner.roomrentalmanager.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.LocalTime;

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
        if (startdatetime.toLocalTime().isBefore(LocalTime.of(9, 0, 0, 0))) {
            throw new IllegalArgumentException("오전 9시부터 대여가능합니다");
        }
        if (startdatetime.toLocalTime().isAfter(LocalTime.of(17, 51, 0, 0))) {
            throw new IllegalArgumentException("마지막 대여 시각은 오후 5시 50분까지 입니다");
        }
        this.startdatetime = startdatetime;
    }

    public LocalDateTime getEnddatetime() {
        return enddatetime;
    }

    public void setEnddatetime(LocalDateTime enddatetime) {
        if (enddatetime.toLocalTime().isAfter(LocalTime.of(18, 1, 0, 0))) {
            throw new IllegalArgumentException("대여 종료 시각은 오후 6시입니다");
        }
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
