package com.ssafy.campinity.core.repository.campsite;


import com.ssafy.campinity.core.entity.campsite.Industry;
import com.ssafy.campinity.core.entity.campsite.OpenSeason;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpenSeasonRepository extends JpaRepository<OpenSeason, Integer> {

    OpenSeason findBySeasonName(String seasonName);

}
