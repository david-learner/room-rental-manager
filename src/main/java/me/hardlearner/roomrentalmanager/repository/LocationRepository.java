package me.hardlearner.roomrentalmanager.repository;

import me.hardlearner.roomrentalmanager.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
