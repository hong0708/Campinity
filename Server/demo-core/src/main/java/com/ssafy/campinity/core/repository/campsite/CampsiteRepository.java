package com.ssafy.campinity.core.repository.campsite;

import com.ssafy.campinity.core.entity.campsite.Campsite;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CampsiteRepository extends JpaRepository<Campsite, Integer> {
    List<Campsite> getCampsitesByLatitudeBetweenAndLongitudeBetween(Double bottomRightLat, Double topLeftLat,
                                                                    Double topLeftLng, Double bottomRightLng);

    Optional<Campsite> findById(int id);

    Optional<Campsite> findByContentId(int contentId);

    Optional<Campsite> findByUuid(UUID campsiteId);

    @Query(value = "SELECT * FROM campsite AS c INNER JOIN campsite_scrap ON campsite_scrap.member_id = :memberId AND campsite_scrap.campsite_id = c.id", nativeQuery = true)
    List<Campsite> findCampsiteScrapList(@Param("memberId") int memberId);
}
