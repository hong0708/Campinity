package com.ssafy.campinity.core.entity.campsite;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class CampsiteEntityTest {

    @Test
    void CampsiteTest(){

        Campsite campsite = new Campsite();
        campsite.setCampName("test1");
        campsite.setUuid(UUID.randomUUID());

        OpenSeason openSeason = new OpenSeason();
        openSeason.setSeasonName("봄");

        OpenSeason openSeason1 = new OpenSeason();
        openSeason1.setSeasonName("여름");

        CampsiteAndOpenSeason campsiteAndOpenSeason = new CampsiteAndOpenSeason(campsite, openSeason);
        CampsiteAndOpenSeason campsiteAndOpenSeason1 = new CampsiteAndOpenSeason(campsite, openSeason1);

        List<CampsiteAndOpenSeason> campsiteOpenseasons = Lists.newArrayList(campsiteAndOpenSeason, campsiteAndOpenSeason1);
        campsite.setOpenSeasons(campsiteOpenseasons);

        assertNotNull(campsite.getUuid(), "campsite uuid is null");

    }
}