package me.hardlearner.roomrentalmanager.repository;

import me.hardlearner.roomrentalmanager.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(value = "SELECT * FROM EVENT WHERE date(start_date_time) = :localDate", nativeQuery = true)
    List<Event> findAllByStartDateTimeEquals(@Param("localDate") String localDate);
}
