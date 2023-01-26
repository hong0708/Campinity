package com.ssafy.campinity.core.repository.campsite;

import com.ssafy.campinity.core.entity.campsite.CampsiteImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampsiteImageRepository extends JpaRepository<CampsiteImage, Integer> {
    List<CampsiteImage> findByCampsite_id(int campsiteId);
}
