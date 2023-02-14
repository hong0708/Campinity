package com.ssafy.campinity.core.repository.campsite;

import com.ssafy.campinity.core.entity.campsite.RegionLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegionLocationRepository extends JpaRepository<RegionLocation, Integer> {

    List<RegionLocation> findBySigunguName(String sigunguName);

//    List<RegionLocation> findByDoName(String doName);

    Optional<RegionLocation> findByDoNameAndSigunguName(String doName, String sigunguName);

//    @Query(value = "SELECT DISTINCT do_name FROM region_location", nativeQuery = true)
//    List<Map<String, Integer>> getDoNameList();
//
//    @Query(value = "SELECT DISTINCT do_name FROM region_location", nativeQuery = true)
//    List<String> getSigunguList();

}
