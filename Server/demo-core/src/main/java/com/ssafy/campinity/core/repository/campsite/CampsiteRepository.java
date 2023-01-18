package com.ssafy.campinity.core.repository.campsite;

import com.ssafy.campinity.core.entity.campsite.Campsite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CampsiteRepository extends JpaRepository<Campsite, Integer> {
    List<Campsite> getCampsitesByLatitudeBetweenAndLongitudeBetween(Double topLeftLat, Double topLeftLng,
                                                                    Double bottomRightLat, Double bottomRightLng);

    Optional<Campsite> findById(int id);

    Optional<Campsite> findByUuid(UUID campsiteId);
}
