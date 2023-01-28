package com.ssafy.campinity.core.repository.curation;

import com.ssafy.campinity.core.entity.curation.CurationImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurationImageRepository extends JpaRepository<CurationImage, Integer> {

    List<CurationImage> findByCuration_id(int curationId);

}
