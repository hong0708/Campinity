package com.ssafy.campinity.core.repository.campsite;

import com.ssafy.campinity.core.entity.campsite.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class CampsiteAndThemeRepositoryTest {


    @Autowired
    EntityManager em;

    @Autowired
    CampsiteRepository campsiteRepository;

    @Autowired
    ThemeRepository themeRepository;

    @Autowired
    CampsiteAndThemeRepository campsiteAndThemeRepository;

    @Test
    @DisplayName("캠핑장-테마 맵핑 테이블 리스너 테스트")
    void CampsiteAndThemeListenerTest(){

        Campsite campsite = new Campsite();
        campsite.setCampName("test1");
        campsite.setUuid(UUID.randomUUID());
        Campsite campsite1 = campsiteRepository.save(campsite);

        Theme theme = new Theme();
        theme.setThemeName("theme1");
        Theme theme1 = themeRepository.save(theme);

        Theme theme2 = new Theme();
        theme2.setThemeName("theme2");
        Theme theme3 = themeRepository.save(theme2);

        CampsiteAndTheme campsiteAndTheme = new CampsiteAndTheme();
        campsiteAndTheme.setCampsite(campsite1);
        campsiteAndTheme.setTheme(theme1);
        CampsiteAndTheme campsiteAndTheme1 = campsiteAndThemeRepository.save(campsiteAndTheme);

        em.flush();
        assertNotNull(campsiteAndTheme1.getCreatedAt());
        assertNotNull(campsiteAndTheme1.getUpdatedAt());

        campsiteAndTheme1.setTheme(theme3);
        CampsiteAndTheme campsiteAndTheme2 = campsiteAndThemeRepository.save(campsiteAndTheme1);
        em.flush();
        assertNotEquals(campsiteAndTheme2.getCreatedAt(), campsiteAndTheme2.getUpdatedAt());
    }
}