package me.hardlearner.roomrentalmanager.repository;

import me.hardlearner.roomrentalmanager.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByRoomNo(int roomNo);
}
