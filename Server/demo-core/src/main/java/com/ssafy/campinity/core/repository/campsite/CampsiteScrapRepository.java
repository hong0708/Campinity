package com.ssafy.campinity.core.repository.campsite;

import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.campsite.CampsiteScrap;
import com.ssafy.campinity.core.entity.member.Member;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CampsiteScrapRepository extends JpaRepository<CampsiteScrap, Integer> {
    Optional<CampsiteScrap> findByMember_idAndCampsite_id(int memberId, int campsiteId);

//    @Query(value = "SELECT campsite_scrap.member_id FROM campsite_scrap INNER JOIN member ON member.id = :memberId AND campsite_scrap.member_id = :memberId " +
//            "INNER JOIN campsite ON campsite.id = :campsiteId AND campsite_scrap.campsite_id = :campsiteId", nativeQuery = true)
//    Optional<Integer> findCampsiteScrap(@Param("memberId") int memberId, @Param("campsiteId") int campsiteId);
}
