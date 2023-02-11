package com.ssafy.campinity.core.repository.campsite;

import com.ssafy.campinity.core.entity.campsite.CaravanFclty;
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
public class CaravanFcltyRepositoryTest {
    @Autowired
    EntityManager em;

    @Autowired
    CaravanFcltyRepository caravanFcltyRepository;

    @Test
    @DisplayName("카라반내부시설 엔티티 리스너 테스트")
    void CaravanFcltyListenerTest() {
        CaravanFclty caravanFclty = new CaravanFclty();
        caravanFclty.setFcltyName("caravanFclty1");

        CaravanFclty caravanFclty1 = caravanFcltyRepository.save(caravanFclty);

        em.flush();

        assertNotNull(caravanFclty1.getCreatedAt());
        assertNotNull(caravanFclty1.getUpdatedAt());

        caravanFclty1.setFcltyName("caravanFclty2");
        CaravanFclty caravanFclty2 = caravanFcltyRepository.save(caravanFclty1);

        em.flush();
        assertNotSame(caravanFclty2.getCreatedAt(), caravanFclty2.getUpdatedAt());

    }
}
