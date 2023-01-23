package com.ssafy.campinity.core.repository.campsite;

import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.campsite.CampsiteAndCaravanFclty;
import com.ssafy.campinity.core.entity.campsite.CaravanFclty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@Transactional
@SpringBootTest
public class CampsiteAndCaravanFcltyRepositoryTest {

    @Autowired
    CampsiteAndCaravanFcltyRepository campsiteAndCaravanFcltyRepository;

    @Autowired
    CampsiteRepository campsiteRepository;

    @Autowired
    CaravanFcltyRepository caravanFcltyRepository;

    @Test
    @DisplayName("캠핑장-카라반내부시설 맵핑 테이블 리스너 테스트")
    void campsiteAndCaravanFcltyListenerTest() {
        Campsite campsite = new Campsite();
        campsite.setCampName("test1");
        campsite.setUuid(UUID.randomUUID());
        Campsite campsite1 = campsiteRepository.save(campsite);

        CaravanFclty caravanFclty = new CaravanFclty();
        caravanFclty.setFcltyName("caravan1");
        CaravanFclty caravanFclty2 = caravanFcltyRepository.save(caravanFclty);

        CaravanFclty caravanFclty1 = new CaravanFclty();
        caravanFclty1.setFcltyName("caravan2");
        CaravanFclty caravanFclty3 = caravanFcltyRepository.save(caravanFclty1);

        CampsiteAndCaravanFclty campsiteAndCaravanFclty = new CampsiteAndCaravanFclty();
        campsiteAndCaravanFclty.setCampsite(campsite1);
        campsiteAndCaravanFclty.setCaravanFclty(caravanFclty2);
        CampsiteAndCaravanFclty campsiteAndCaravanFclty1 = campsiteAndCaravanFcltyRepository.save(campsiteAndCaravanFclty);

        assertNotNull(campsiteAndCaravanFclty1.getCreatedAt());
        assertNotNull(campsiteAndCaravanFclty1.getUpdatedAt());

        campsiteAndCaravanFclty1.setCaravanFclty(caravanFclty3);
        CampsiteAndCaravanFclty campsiteAndCaravanFclty2 = campsiteAndCaravanFcltyRepository.save(campsiteAndCaravanFclty1);
        assertNotEquals(campsiteAndCaravanFclty2.getUpdatedAt(), campsiteAndCaravanFclty1.getUpdatedAt());

        campsiteAndCaravanFcltyRepository.deleteAllInBatch();
        campsiteRepository.deleteAllInBatch();
        caravanFcltyRepository.deleteAllInBatch();
    }
}


