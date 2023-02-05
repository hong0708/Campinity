package com.ssafy.campinity.core.repository.campsite;

import com.ssafy.campinity.core.entity.campsite.Amenity;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.campsite.CampsiteAndAmenity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
@Transactional
@SpringBootTest
class AmenityRepositoryTest {

    @Autowired
    AmenityRepository amenityRepository;
    @Autowired
    CampsiteRepository campsiteRepository;
    @Autowired
    CampsiteAndAmenityRepository campsiteAndAmenityRepository;

    @Test
    @DisplayName("부대시설 엔터티 리스너 테스트")
    void AmenityListenerTest() throws Exception {

        Amenity amenity = new Amenity();
        amenity.setAmenityName("amenity1");
        Amenity amenity1 = amenityRepository.save(amenity);

        Campsite campsite = campsiteRepository.findById(1).orElseThrow();

        CampsiteAndAmenity campsiteAndAmenity = CampsiteAndAmenity.builder()
                .amenity(amenity1)
                .campsite(campsite)
                .build();

        CampsiteAndAmenity campsiteAndAmenity1 = campsiteAndAmenityRepository.save(campsiteAndAmenity);

        assertEquals("amenity1", campsiteAndAmenity1.getAmenity().getAmenityName());
        System.out.println("test " + campsite.getAmenities().toString());
    }
}
