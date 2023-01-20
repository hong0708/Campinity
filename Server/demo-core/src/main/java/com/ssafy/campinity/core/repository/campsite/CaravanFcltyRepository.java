package com.ssafy.campinity.core.repository.campsite;

import com.ssafy.campinity.core.entity.campsite.Amenity;
import com.ssafy.campinity.core.entity.campsite.CaravanFclty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CaravanFcltyRepository extends JpaRepository<CaravanFclty, Integer> {
    Optional<CaravanFclty> findByFcltyName(String fcltyName);

}
