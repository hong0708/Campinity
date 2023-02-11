package com.ssafy.campinity.core.repository.campsite;

import com.ssafy.campinity.core.entity.campsite.OpenSeason;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

@Transactional
@SpringBootTest
public class OpenSeasonRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    AmenityRepository amenityRepository;
    @Autowired
    OpenSeasonRepository openSeasonRepository;

    @Test
    @DisplayName("운영계절 엔티티 리스터 테스트")
    void OpenSeasonListenerTest() {
        OpenSeason openSeason = new OpenSeason();
        openSeason.setSeasonName("여름");
        OpenSeason openSeason1 = openSeasonRepository.save(openSeason);

        assertNotNull(openSeason1.getCreatedAt());
        assertNotNull(openSeason1.getUpdatedAt());

        openSeason1.setSeasonName("겨울");
        OpenSeason openSeason2 = openSeasonRepository.save(openSeason1);

        em.flush();

        assertNotSame(openSeason1.getUpdatedAt(), openSeason2.getCampsiteAndOpenSeasons());
    }

}
