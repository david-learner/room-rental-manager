package me.hardlearner.roomrentalmanager.repository;

import me.hardlearner.roomrentalmanager.domain.Event;
import me.hardlearner.roomrentalmanager.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(value = "SELECT * FROM EVENT WHERE date(start_date_time) = :localDate", nativeQuery = true)
    List<Event> findAllByStartDateTimeEquals(@Param("localDate") String localDate);

//    @Query(value = "SELECT * FROM EVENT WHERE date(start_date_time) = :localDate AND location_id = :locationId", nativeQuery = true)
    @Query(value = "SELECT * FROM EVENT WHERE DATE(start_date_time) = :localDate AND location_id = (SELECT id FROM LOCATION WHERE room_no = :roomNo)", nativeQuery = true)
    List<Event> findAllByStartDateTimeEqualsAndLocationEquals(@Param("localDate") String localDate, @Param("roomNo") int roomNo);
}
