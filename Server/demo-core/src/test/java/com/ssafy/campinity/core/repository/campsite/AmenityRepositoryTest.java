package com.ssafy.campinity.core.repository.campsite;

import com.ssafy.campinity.core.entity.campsite.Amenity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
@Transactional
@SpringBootTest
class AmenityRepositoryTest {

    @Autowired
    AmenityRepository amenityRepository;

    @Test
    @DisplayName("부대시설 엔터티 리스너 테스트")
    void AmenityListenerTest(){

        Amenity amenity = new Amenity();
        amenity.setAmenityName("amenity1");
        Amenity amenity1 = amenityRepository.save(amenity);

        assertNotNull(amenity1.getCreatedAt());
        assertNotNull(amenity1.getUpdatedAt());

        amenity1.setAmenityName("amenity2");
        Amenity amenity2 = amenityRepository.save(amenity1);

        assertNotSame(amenity1.getUpdatedAt(), amenity2.getUpdatedAt());

        amenityRepository.deleteAllInBatch();


    }
}
