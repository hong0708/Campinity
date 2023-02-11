package com.ssafy.campinity.core.repository.campsite;

import com.ssafy.campinity.core.entity.campsite.GlampFclty;
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
public class GlampFcltyRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    GlampFcltyRepository glampFcltyRepository ;

    @Test
    @DisplayName("글램핑내부시설 엔티티 리스너 테스트")
    void GlampFcltyListenerTest() {
        GlampFclty glampFclty = new GlampFclty();
        glampFclty.setFcltyName("glamp1");

        GlampFclty glampFclty1 = glampFcltyRepository.save(glampFclty);

        em.flush();

        assertNotNull(glampFclty1.getCreatedAt());
        assertNotNull(glampFclty1.getUpdatedAt());

        glampFclty1.setFcltyName("glamp2");
        GlampFclty glampFclty2 = glampFcltyRepository.save(glampFclty1);

        em.flush();
        assertNotSame(glampFclty2.getCreatedAt(), glampFclty2.getUpdatedAt());
    }

}
