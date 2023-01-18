package com.ssafy.campinity.core.repository.campsite;

import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.campsite.CampsiteScrap;
import com.ssafy.campinity.core.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CampsiteScrapRepository extends JpaRepository<CampsiteScrap, Integer> {
    Optional<CampsiteScrap> findByUserAndCampsite(User user, Campsite campsite);
}
