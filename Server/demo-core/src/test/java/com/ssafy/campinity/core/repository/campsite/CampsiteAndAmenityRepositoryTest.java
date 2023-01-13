package com.ssafy.campinity.core.repository.campsite;

import com.ssafy.campinity.core.entity.campsite.Amenity;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.campsite.CampsiteAndAmenity;
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
    void CampsiteAndAmenityListenerTest(){

        Campsite campsite = new Campsite();
        campsite.setCampName("test1");
        campsite.setUuid(UUID.randomUUID());
        campsiteRepository.save(campsite);

        Amenity amenity = new Amenity();
        amenity.setAmenityName("amenity1");
        amenityRepository.save(amenity);

        Amenity amenity1 = new Amenity();
        amenity.setAmenityName("amenity2");
        amenityRepository.save(amenity1);

        Campsite campsite1 = campsiteRepository.findById(1).get();
        Amenity amenity2 = amenityRepository.findById(1).get();
        Amenity amenity3 = amenityRepository.findById(2).get();

        CampsiteAndAmenity campsiteAndAmenity = new CampsiteAndAmenity();
        campsiteAndAmenity.setAmenity(amenity2);
        campsiteAndAmenity.setCampsite(campsite1);

        campsiteAndAmenityRepository.save(campsiteAndAmenity);
        CampsiteAndAmenity campsiteAndAmenity1 = campsiteAndAmenityRepository.findById(1).get();

        assertNotNull(campsiteAndAmenity1.getCreatedAt());
        assertNotNull(campsiteAndAmenity1.getUpdatedAt());

        campsiteAndAmenity1.setAmenity(amenity3);
        campsiteAndAmenityRepository.save(campsiteAndAmenity1);
        CampsiteAndAmenity campsiteAndAmenity2 = campsiteAndAmenityRepository.findById(1).get();
        assertNotEquals(campsiteAndAmenity2.getCreatedAt(), campsiteAndAmenity2.getUpdatedAt());

        campsiteAndAmenityRepository.deleteAllInBatch();
        amenityRepository.deleteAllInBatch();
        campsiteRepository.deleteAllInBatch();

    }
}