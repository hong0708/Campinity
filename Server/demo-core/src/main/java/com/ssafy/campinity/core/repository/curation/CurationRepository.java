package com.ssafy.campinity.core.repository.curation;

import com.ssafy.campinity.core.dto.CurationScrapListResDTO;
import com.ssafy.campinity.core.entity.curation.Curation;
import com.ssafy.campinity.core.entity.curation.CurationCategory;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CurationRepository extends JpaRepository<Curation, Integer> {
    @Query(value = "SELECT * FROM curation order by RAND() limit 3", nativeQuery = true)
    List<Curation> findRandomCuration();

    @Query(value = "SELECT c.id, c.created_at, c.updated_at, c.content, c.title, c.uuid, c.curation_category, c.first_image_path FROM curation AS c INNER JOIN curation_scrap ON curation_scrap.member_id = :memberId AND curation_scrap.curation_id = c.id", nativeQuery = true)
    List<Curation> findScrapList(@Param("memberId") int memberId);

    List<Curation> findByCurationCategory(CurationCategory curationCategory);

    Optional<Curation> findByUuid(UUID curationId);
}
