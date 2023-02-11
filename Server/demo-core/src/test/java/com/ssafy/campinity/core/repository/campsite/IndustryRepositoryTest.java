package com.ssafy.campinity.core.repository.campsite;

import com.ssafy.campinity.core.entity.campsite.Industry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;


@Transactional
@SpringBootTest
class IndustryRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    IndustryRepository industryRepository;

    @Test
    @DisplayName("사업 엔터티 리스너 테스트")
    void IndustryListenerTest() {

        Industry industry = new Industry();
        industry.setIndustryName("industry1");
        Industry industry1 = industryRepository.save(industry);
        em.flush();
        assertNotNull(industry1.getCreatedAt());
        assertNotNull(industry1.getUpdatedAt());

        industry1.setIndustryName("amenity2");
        Industry industry2 = industryRepository.save(industry1);
        em.flush();

        assertNotSame(industry2.getCreatedAt(), industry2.getUpdatedAt());
    }
}