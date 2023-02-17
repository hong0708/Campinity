package com.ssafy.campinity.core.repository.campsite;

import com.ssafy.campinity.core.entity.campsite.Industry;
import com.ssafy.campinity.core.entity.campsite.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ThemeRepository extends JpaRepository<Theme, Integer> {
    Optional<Theme> findByThemeName(String themeName);

}
