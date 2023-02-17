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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;


@SpringBootTest
@Transactional
@ActiveProfiles("test")
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

        Campsite campsite1 = Campsite.builder().doName("캠핑군").uuid(UUID.randomUUID()).messages(new ArrayList<>()).build();
        Campsite camp = campsiteRepository.save(campsite1);

        campsiteService.scrap(savedMember.getId(), camp.getUuid());

        CampsiteScrap campsiteScrap1 = campsiteScrapRepository.findByMember_idAndCampsite_id(savedMember.getId(), camp.getId()).orElseThrow(IllegalArgumentException::new);

        Assertions.assertThat(campsiteScrap1.getCampsite().getUuid()).isEqualTo(camp.getUuid());

        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            campsiteService.scrap(savedMember.getId(), camp.getUuid());
            CampsiteScrap campsiteScrap2 = campsiteScrapRepository.findByMember_idAndCampsite_id(savedMember.getId(), camp.getId()).orElseThrow(IllegalArgumentException::new);
        });
    }

    @Test
    @DisplayName("scrap 오류 발생 테스트")
    public void scrapErrorTest (){
//        UUID campsiteId = UUID.randomUUID();
//
//        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
//            campsiteService.scrap(10, campsiteId);
//        });
    }
}
