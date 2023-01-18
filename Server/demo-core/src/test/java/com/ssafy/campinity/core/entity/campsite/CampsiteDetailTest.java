package com.ssafy.campinity.core.entity.campsite;

import com.ssafy.campinity.core.service.impl.CampsiteServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.util.UUID;

@SpringBootTest
@Transactional
public class CampsiteDetailTest {

    @Autowired
    CampsiteServiceImpl campsiteService;

    @Test
    @DisplayName("campsite detail 조회 exception 테스트")
    public void scrapErrorTest (){
        UUID userId = UUID.randomUUID();
        UUID campsiteId = UUID.randomUUID();

        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            campsiteService.getCampsiteDetail(userId, campsiteId);
        });
    }
}
