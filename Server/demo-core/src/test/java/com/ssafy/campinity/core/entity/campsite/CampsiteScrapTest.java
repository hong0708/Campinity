package com.ssafy.campinity.core.entity.campsite;

import com.ssafy.campinity.core.entity.user.User;
import com.ssafy.campinity.core.repository.campsite.CampsiteRepository;
import com.ssafy.campinity.core.repository.campsite.CampsiteScrapRepository;
import com.ssafy.campinity.core.repository.campsite.UserRepository;
import com.ssafy.campinity.core.service.impl.CampsiteServiceImpl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@SpringBootTest
@Transactional
public class CampsiteScrapTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CampsiteScrapRepository campsiteScrapRepository;

    @Autowired
    CampsiteServiceImpl campsiteService;

    @Autowired
    CampsiteRepository campsiteRepository;

    @Test
    @DisplayName("scrap 스크랩, 해제 기능 테스트")
    public void scrapTest () {
        User user = User.builder().name("test").uuid(UUID.randomUUID()).build();
        User savedUser = userRepository.save(user);

        Campsite camp = campsiteRepository.findById(1).orElseThrow(IllegalArgumentException::new);

        campsiteService.scrap(savedUser.getUuid(), camp.getUuid());

        CampsiteScrap campsiteScrap1 = campsiteScrapRepository.findByUserAndCampsite(savedUser, camp).orElseThrow(IllegalArgumentException::new);

        Assertions.assertThat(campsiteScrap1.getScrapType()).isEqualTo(true);

        campsiteService.scrap(savedUser.getUuid(), camp.getUuid());

        CampsiteScrap campsiteScrap2 = campsiteScrapRepository.findByUserAndCampsite(savedUser, camp).orElseThrow(IllegalArgumentException::new);

        Assertions.assertThat(campsiteScrap2.getScrapType()).isEqualTo(false);
    }

    @Test
    @DisplayName("scrap 오류 발생 테스트")
    public void scrapErrorTest (){
        UUID userId = UUID.randomUUID();
        UUID campsiteId = UUID.randomUUID();

        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            campsiteService.scrap(userId, campsiteId);
        });
    }
}
