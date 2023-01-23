package com.ssafy.campinity.core.repository.campsite;

import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.campsite.CampsiteAndGlampFclty;
import com.ssafy.campinity.core.entity.campsite.GlampFclty;
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
public class CampsiteAndGlampFcltyRepositoryTest {

    @Autowired
    CampsiteRepository campsiteRepository;

    @Autowired
    GlampFcltyRepository glampFcltyRepository;

    @Autowired
    CampsiteAndGlampFcltyRepository campsiteAndGlampFcltyRepository;

    @Test
    @DisplayName("캠핑장-글램핑내부시설 맵핑 테이블 리스너 테스트")
    void campsiteAndGlampFcltyFcltyListenerTest() {
        Campsite campsite = new Campsite();
        campsite.setCampName("test1");
        campsite.setUuid(UUID.randomUUID());
        Campsite campsite1 = campsiteRepository.save(campsite);

        GlampFclty glampFclty = new GlampFclty();
        glampFclty.setFcltyName("glamp1");
        GlampFclty glampFclty2 = glampFcltyRepository.save(glampFclty);

        GlampFclty glampFclty1 = new GlampFclty();
        glampFclty1.setFcltyName("glamp2");
        GlampFclty glampFclty3 = glampFcltyRepository.save(glampFclty1);

        CampsiteAndGlampFclty campsiteAndGlampFclty = new CampsiteAndGlampFclty();
        campsiteAndGlampFclty.setCampsite(campsite1);
        campsiteAndGlampFclty.setGlampFclty(glampFclty2);
        CampsiteAndGlampFclty campsiteAndGlampFclty1 = campsiteAndGlampFcltyRepository.save(campsiteAndGlampFclty);

        assertNotNull(campsiteAndGlampFclty1.getCreatedAt());
        assertNotNull(campsiteAndGlampFclty1.getUpdatedAt());

        campsiteAndGlampFclty1.setGlampFclty(glampFclty3);
        CampsiteAndGlampFclty campsiteAndGlampFclty2 = campsiteAndGlampFcltyRepository.save(campsiteAndGlampFclty1);
        assertNotEquals(campsiteAndGlampFclty2.getUpdatedAt(), campsiteAndGlampFclty1.getUpdatedAt());

        campsiteAndGlampFcltyRepository.deleteAllInBatch();
        campsiteRepository.deleteAllInBatch();
        glampFcltyRepository.deleteAllInBatch();
    }

}
