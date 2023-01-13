package com.ssafy.campinity.core.repository.campsite;

import com.ssafy.campinity.core.entity.campsite.Amenity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

@SpringBootTest
class AmenityRepositoryTest {

    @Autowired
    AmenityRepository amenityRepository;

    @Test
    void AmenityListenerTest(){

        Amenity amenity = new Amenity();
        amenity.setAmenityName("amenity1");

        amenityRepository.save(amenity);
        Amenity amenity1 = amenityRepository.findById(1).stream().findFirst().get();
        System.out.println(amenity1);

        assertNotNull(amenity1.getCreatedAt());
        assertNotNull(amenity1.getUpdatedAt());

        amenity1.setAmenityName("amenity2");
        amenityRepository.save(amenity1);

        Amenity amenity2 = amenityRepository.findById(1).stream().findFirst().get();

        assertNotSame(amenity1.getUpdatedAt(), amenity2.getUpdatedAt());


    }
}
