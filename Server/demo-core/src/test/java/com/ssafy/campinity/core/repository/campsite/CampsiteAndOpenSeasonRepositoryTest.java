package com.ssafy.campinity.core.repository.campsite;

import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.campsite.CampsiteAndOpenSeason;
import com.ssafy.campinity.core.entity.campsite.OpenSeason;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;


@Transactional
@SpringBootTest
public class CampsiteAndOpenSeasonRepositoryTest {
    @Autowired
    CampsiteRepository campsiteRepository;

    @Autowired
    OpenSeasonRepository openSeasonRepository;

    @Autowired
    CampsiteAndOpenSeasonRepository campsiteAndOpenSeasonRepository;


    @Test
    @DisplayName("캠핑장-운영계절 맵핑 테이블 리스너 테스트")
    void campsiteAndOpenSeasonListenerTest() {
        Campsite campsite = new Campsite();
        campsite.setCampName("test1");
        campsite.setUuid(UUID.randomUUID());
        Campsite campsite1 = campsiteRepository.save(campsite);

        OpenSeason openSeason = new OpenSeason();
        openSeason.setSeasonName("여름");
        OpenSeason openSeason2 = openSeasonRepository.save(openSeason);

        OpenSeason openSeason1 = new OpenSeason();
        openSeason1.setSeasonName("겨울");
        OpenSeason openSeason3 = openSeasonRepository.save(openSeason1);

        CampsiteAndOpenSeason campsiteAndOpenSeason = new CampsiteAndOpenSeason();
        campsiteAndOpenSeason.setCampsite(campsite1);
        campsiteAndOpenSeason.setOpenSeason(openSeason2);
        CampsiteAndOpenSeason campsiteAndOpenSeason1 = campsiteAndOpenSeasonRepository.save(campsiteAndOpenSeason);

        assertNotNull(campsiteAndOpenSeason1.getCreatedAt());
        assertNotNull(campsiteAndOpenSeason1.getUpdatedAt());

        campsiteAndOpenSeason1.setOpenSeason(openSeason3);
        CampsiteAndOpenSeason campsiteAndOpenSeason2 = campsiteAndOpenSeasonRepository.save(campsiteAndOpenSeason1);
        assertNotEquals(campsiteAndOpenSeason2.getUpdatedAt(), campsiteAndOpenSeason1.getUpdatedAt());

        campsiteAndOpenSeasonRepository.deleteAllInBatch();
        campsiteRepository.deleteAllInBatch();
        openSeasonRepository.deleteAllInBatch();
    }
}
