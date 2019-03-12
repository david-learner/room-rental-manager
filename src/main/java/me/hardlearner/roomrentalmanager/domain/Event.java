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

    public Event(String lessorName, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.lessorName = lessorName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public Event(Location location, String lessorName, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.location = location;
        this.lessorName = lessorName;
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
}
