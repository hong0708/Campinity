package com.ssafy.campinity.core.repository.campsite;

import com.ssafy.campinity.core.entity.campsite.Amenity;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.campsite.CampsiteAndAmenity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
@Transactional
@SpringBootTest
@ActiveProfiles("test")
class AmenityRepositoryTest {
    @Autowired
    EntityManager em;

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

        Campsite campsite = Campsite.builder().doName("캠핑군").uuid(UUID.randomUUID()).messages(new ArrayList<>()).build();
        Campsite savedCampsite = campsiteRepository.save(campsite);

        CampsiteAndAmenity campsiteAndAmenity = CampsiteAndAmenity.builder()
                .amenity(amenity1)
                .campsite(savedCampsite)
                .build();

        CampsiteAndAmenity campsiteAndAmenity1 = campsiteAndAmenityRepository.save(campsiteAndAmenity);

        assertEquals("amenity1", campsiteAndAmenity1.getAmenity().getAmenityName());
    }
}
