package me.hardlearner.roomrentalmanager.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    Location location;
    private String lessorName;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime startDateTime;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime endDateTime;

    public Event() {
    }

    public Event(Location location, String lessorName, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.location = location;
        this.lessorName = lessorName;
        if (startDateTime.isAfter(endDateTime) || startDateTime.equals(endDateTime)) {
            throw new IllegalArgumentException("시작시간은 종료시간보다 이른시간이어야 합니다");
        }
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getLessorName() {
        return lessorName;
    }

    public void setLessorName(String lessorName) {
        this.lessorName = lessorName;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", location=" + location +
                ", lessorName='" + lessorName + '\'' +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                '}';
    }

    public void update(EventInputDto dto, Location location) {
        this.location = location;
        this.lessorName = dto.getLessorname();
        this.startDateTime = dto.getStartdatetime();
        this.endDateTime = dto.getEnddatetime();
    }

    public boolean isOverlap(Event secondEvent) {
        if (!this.location.equals(secondEvent.location)) {
            throw new IllegalStateException("서로 다른 장소입니다");
        }
        return this.endDateTime.isAfter(secondEvent.startDateTime);
    }
}
