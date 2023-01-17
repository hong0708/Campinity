package com.ssafy.campinity.core.repository.campsite;

import com.ssafy.campinity.core.entity.campsite.Campsite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampsiteRepository extends JpaRepository<Campsite, Integer> {
    List<Campsite> getCampsitesByLatitudeBetweenAndLongitudeBetween(Double topLeftLat, Double topLeftLng,
                                                                    Double bottomRightLat, Double bottomRightLng);

    Campsite findByContentId(int contentId);

    List<Campsite> findAll();

    Campsite findById(int id);
}
