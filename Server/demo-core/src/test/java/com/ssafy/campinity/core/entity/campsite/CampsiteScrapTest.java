package com.ssafy.campinity.core.entity.campsite;

import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.repository.campsite.CampsiteRepository;
import com.ssafy.campinity.core.repository.campsite.CampsiteScrapRepository;
import com.ssafy.campinity.core.repository.member.MemberRepository;
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
    MemberRepository memberRepository;

    @Autowired
    CampsiteScrapRepository campsiteScrapRepository;

    @Autowired
    CampsiteServiceImpl campsiteService;

    @Autowired
    CampsiteRepository campsiteRepository;

    @Test
    @DisplayName("scrap 스크랩, 해제 기능 테스트")
    public void scrapTest () {
        Member member = Member.builder().name("test").uuid(UUID.randomUUID()).build();
        Member savedMember = memberRepository.save(member);

        Campsite camp = campsiteRepository.findById(1).orElseThrow(IllegalArgumentException::new);

        campsiteService.scrap(savedMember.getId(), camp.getUuid());

        CampsiteScrap campsiteScrap1 = campsiteScrapRepository.findByMember_idAndCampsite_id(savedMember.getId(), camp.getId()).orElseThrow(IllegalArgumentException::new);

        Assertions.assertThat(campsiteScrap1.getScrapType()).isEqualTo(true);

        campsiteService.scrap(savedMember.getId(), camp.getUuid());

        CampsiteScrap campsiteScrap2 = campsiteScrapRepository.findByMember_idAndCampsite_id(savedMember.getId(), camp.getId()).orElseThrow(IllegalArgumentException::new);

        Assertions.assertThat(campsiteScrap2.getScrapType()).isEqualTo(false);
    }

    @Test
    @DisplayName("scrap 오류 발생 테스트")
    public void scrapErrorTest (){
//        UUID userId = UUID.randomUUID();
//        UUID campsiteId = UUID.randomUUID();
//
//        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
//            campsiteService.scrap(userId, campsiteId);
//        });
    }
}
