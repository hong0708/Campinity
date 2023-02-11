package com.ssafy.campinity.core.repository.campsite;

import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.campsite.CampsiteAndIndustry;
import com.ssafy.campinity.core.entity.campsite.Industry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transactional
@SpringBootTest
class CampsiteAndIndustryRepositoryTest {
    @Autowired
    EntityManager em;

    @Autowired
    CampsiteRepository campsiteRepository;

    @Autowired
    IndustryRepository industryRepository;

    @Autowired
    CampsiteAndIndustryRepository campsiteAndIndustryRepository;

    @Test
    @DisplayName("캠핑장-사업 맵핑 테이블 리스너 테스트")
    void CampsiteAndAmenityListenerTest(){

        Campsite campsite = new Campsite();
        campsite.setCampName("test1");
        campsite.setUuid(UUID.randomUUID());
        Campsite campsite1 = campsiteRepository.save(campsite);

        Industry industry = new Industry();
        industry.setIndustryName("industry1");
        Industry industry2 = industryRepository.save(industry);

        Industry industry1 = new Industry();
        industry.setIndustryName("industry2");
        Industry industry3 = industryRepository.save(industry1);

        CampsiteAndIndustry campsiteAndIndustry = new CampsiteAndIndustry();
        campsiteAndIndustry.setCampsite(campsite1);
        campsiteAndIndustry.setIndustry(industry2);
        CampsiteAndIndustry campsiteAndAmenity1 = campsiteAndIndustryRepository.save(campsiteAndIndustry);

        em.flush();

        assertNotNull(campsiteAndAmenity1.getCreatedAt());
        assertNotNull(campsiteAndAmenity1.getUpdatedAt());

        campsiteAndAmenity1.setIndustry(industry3);
        CampsiteAndIndustry campsiteAndAmenity2 = campsiteAndIndustryRepository.save(campsiteAndAmenity1);

        em.flush();

        assertNotEquals(campsiteAndAmenity2.getCreatedAt(), campsiteAndAmenity2.getUpdatedAt());

    }
}