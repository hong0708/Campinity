package com.ssafy.campinity.core.repository.campsite;

import com.ssafy.campinity.core.entity.campsite.Amenity;
import com.ssafy.campinity.core.entity.campsite.CampsiteAndAmenity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampsiteAndAmenityRepository extends JpaRepository<CampsiteAndAmenity, Integer> {

}
