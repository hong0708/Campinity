package com.ssafy.campinity.core.repository.curation;

import com.ssafy.campinity.core.entity.curation.CurationScrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CurationScrapRepository extends JpaRepository<CurationScrap, Integer> {

    Optional<CurationScrap> findByCuration_idAndMember_id(int curationId, int memberId);

}
