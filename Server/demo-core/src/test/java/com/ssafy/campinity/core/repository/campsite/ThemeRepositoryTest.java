package com.ssafy.campinity.core.repository.campsite;

import com.ssafy.campinity.core.entity.campsite.Industry;
import com.ssafy.campinity.core.entity.campsite.Theme;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ThemeRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    ThemeRepository themeRepository;

    @Test
    @DisplayName("테마 엔터티 리스너 테스트")
    void ThemeListenerTest(){

        Theme theme = new Theme();
        theme.setThemeName("theme1");
        Theme theme1 = themeRepository.save(theme);
        em.flush();

        assertNotNull(theme1.getCreatedAt());
        assertNotNull(theme1.getUpdatedAt());

        theme1.setThemeName("theme2");
        Theme theme2 = themeRepository.save(theme1);
        em.flush();

        assertNotSame(theme2.getCreatedAt(), theme2.getUpdatedAt());
    }
}