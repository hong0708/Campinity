package com.ssafy.campinity.core.repository.campsite;

import com.ssafy.campinity.core.entity.campsite.RegionLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionLocationRepository extends JpaRepository<RegionLocation, Integer> {

    List<RegionLocation> findBySigunguName(String sigunguName);

    List<RegionLocation> findByDoName(String doName);

//    @Query(value = "SELECT DISTINCT do_name FROM region_location", nativeQuery = true)
//    List<Map<String, Integer>> getDoNameList();
//
//    @Query(value = "SELECT DISTINCT do_name FROM region_location", nativeQuery = true)
//    List<String> getSigunguList();

}
