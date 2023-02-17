package com.ssafy.campinity.core.service;

import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.repository.campsite.CampsiteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class CampsiteSearchByRangeTest {

    @Autowired
    CampsiteRepository campsiteRepository;

    @Test
    @DisplayName("campsite range retrieve test")
    public void campsiteRangeRetrieveTest() {

        int beforeNumCampsite = campsiteRepository.getCampsitesByLatitudeBetweenAndLongitudeBetween(
                36.1088718, 36.1097202, 128.4187025,128.4204129).size();

        Campsite campsite1 = Campsite.builder()
                .latitude(36.1088718)
                .longitude(128.4187025)
                .campName("골든케어필라테스")
                .doName("경북")
                .sigunguName("구미시")
                .build();

        Campsite campsite2 = Campsite.builder()
                .latitude(36.1097202)
                .longitude(128.4204129)
                .campName("포시즌 클라이밍")
                .doName("경북")
                .sigunguName("구미시")
                .build();

        Campsite campsite3 = Campsite.builder()
                .latitude(36.10941)
                .longitude(128.41942)
                .campName("호텔얌")
                .doName("경북")
                .sigunguName("구미시")
                .build();

        Campsite campsite4 = Campsite.builder()
                .latitude(36.1090229)
                .longitude(128.4189429)
                .campName("피트니스247")
                .doName("경북")
                .sigunguName("구미시")
                .build();

        Campsite campsite5 = Campsite.builder()
                .latitude(36.109055)
                .longitude(128.4195385)
                .campName("호텔제이스")
                .doName("경북")
                .sigunguName("구미시")
                .build();

        campsiteRepository.save(campsite1);
        campsiteRepository.save(campsite2);
        campsiteRepository.save(campsite3);
        campsiteRepository.save(campsite4);
        campsiteRepository.save(campsite5);

        int afterNumCampsite = campsiteRepository.getCampsitesByLatitudeBetweenAndLongitudeBetween(
                        36.1088718, 36.1097202, 128.4187025,128.4204129).size();


        assertEquals(beforeNumCampsite + 5, afterNumCampsite);
    }
}