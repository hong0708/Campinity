package com.ssafy.campinity.core.repository.campsite;

import com.ssafy.campinity.core.entity.campsite.CampsiteAndIndustry;
import com.ssafy.campinity.core.entity.campsite.CampsiteAndTheme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampsiteAndThemeRepository extends JpaRepository<CampsiteAndTheme, Integer> {

}
