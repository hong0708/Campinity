package com.ssafy.campinity.core.repository.campsite;


import com.ssafy.campinity.core.entity.campsite.Industry;
import com.ssafy.campinity.core.entity.campsite.OpenSeason;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OpenSeasonRepository extends JpaRepository<OpenSeason, Integer> {

    Optional<OpenSeason> findBySeasonName(String seasonName);

}
