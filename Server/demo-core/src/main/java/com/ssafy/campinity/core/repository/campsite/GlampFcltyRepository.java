package com.ssafy.campinity.core.repository.campsite;

import com.ssafy.campinity.core.entity.campsite.CaravanFclty;
import com.ssafy.campinity.core.entity.campsite.GlampFclty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GlampFcltyRepository extends JpaRepository<GlampFclty, Integer> {

    Optional<GlampFclty> findByFcltyName(String fcltyName);

}
