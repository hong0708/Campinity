package com.ssafy.campinity.core.repository.campsite;

import com.ssafy.campinity.core.entity.campsite.Amenity;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.campsite.CampsiteAndAmenity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class CampsiteAndAmenityRepositoryTest {

    @Autowired
    AmenityRepository amenityRepository;

    @Autowired
    CampsiteRepository campsiteRepository;

    @Autowired
    CampsiteAndAmenityRepository campsiteAndAmenityRepository;

    @Test
    @DisplayName("캠핑장-부대시설 맵핑 테이블 리스너 테스트")
    void CampsiteAndAmenityListenerTest(){

        Campsite campsite = new Campsite();
        campsite.setCampName("test1");
        campsite.setUuid(UUID.randomUUID());
        Campsite campsite1 = campsiteRepository.save(campsite);

        Amenity amenity = new Amenity();
        amenity.setAmenityName("amenity1");
        Amenity amenity2 = amenityRepository.save(amenity);

        Amenity amenity1 = new Amenity();
        amenity.setAmenityName("amenity2");
        Amenity amenity3 = amenityRepository.save(amenity1);

        CampsiteAndAmenity campsiteAndAmenity = new CampsiteAndAmenity();
        campsiteAndAmenity.setAmenity(amenity2);
        campsiteAndAmenity.setCampsite(campsite1);

        CampsiteAndAmenity campsiteAndAmenity1 = campsiteAndAmenityRepository.save(campsiteAndAmenity);

        assertNotNull(campsiteAndAmenity1.getCreatedAt());
        assertNotNull(campsiteAndAmenity1.getUpdatedAt());

        campsiteAndAmenity1.setAmenity(amenity3);
        CampsiteAndAmenity campsiteAndAmenity2 = campsiteAndAmenityRepository.save(campsiteAndAmenity1);
        assertNotEquals(campsiteAndAmenity2.getCreatedAt(), campsiteAndAmenity2.getUpdatedAt());

        campsiteAndAmenityRepository.deleteAllInBatch();
        amenityRepository.deleteAllInBatch();
        campsiteRepository.deleteAllInBatch();

    }
}