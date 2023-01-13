package com.ssafy.campinity.core.repository.campsite;

import com.ssafy.campinity.core.entity.campsite.Campsite;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;


@SpringBootTest
class CampsiteRepositoryTest {

    @Autowired
    CampsiteRepository campsiteRepository;

    @Test
    void campsiteListenerTest(){

        Campsite campsite = new Campsite();
        campsite.setCampName("test1");
        campsite.setUuid(UUID.randomUUID());

        campsiteRepository.save(campsite);
        Campsite campsite1 = campsiteRepository.findById(1).stream().findFirst().get();
        System.out.println(campsite1);

        assertNotNull(campsite1.getCreatedAt());
        assertNotNull(campsite1.getUpdatedAt());

        campsite1.setCampName("updated1");
        campsiteRepository.save(campsite1);

        Campsite campsite2 = campsiteRepository.findById(1).stream().findFirst().get();

        assertNotSame(campsite1.getUpdatedAt(), campsite2.getUpdatedAt());

    }
}