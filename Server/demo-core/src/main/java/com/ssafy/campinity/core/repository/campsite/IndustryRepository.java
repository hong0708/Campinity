package com.ssafy.campinity.core.repository.campsite;

import com.ssafy.campinity.core.entity.campsite.Amenity;
import com.ssafy.campinity.core.entity.campsite.CaravanFclty;
import com.ssafy.campinity.core.entity.campsite.Industry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IndustryRepository extends JpaRepository<Industry, Integer> {

    Optional<Industry> findByIndustryName(String industryName);

}
