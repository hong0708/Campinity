package com.ssafy.campinity.core.repository.campsite;

import com.ssafy.campinity.core.entity.campsite.CampsiteImage;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface CampsiteImageRepository extends JpaRepository<CampsiteImage, Integer> {
    List<CampsiteImage> findByCampsite_id(int campsiteId);

//    @Query(value = "SELECT campsite_image.image_path FROM campsite_image WHERE campsite_image.campsite_id = :campsiteId", nativeQuery = true)
//    List<String> findImagePathByCampsite(@Param("campsiteId") int campsiteId);

}
