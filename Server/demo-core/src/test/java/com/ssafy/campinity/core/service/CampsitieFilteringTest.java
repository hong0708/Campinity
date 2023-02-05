package com.ssafy.campinity.core.service;

import com.ssafy.campinity.core.dto.CampsiteListResDTO;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.campsite.CampsiteScrap;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.repository.campsite.CampsiteRepository;
import com.ssafy.campinity.core.repository.campsite.CampsiteScrapRepository;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@SpringBootTest
public class CampsitieFilteringTest {

    @Autowired
    CampsiteRepository campsiteRepository;

    @Autowired
    CampsiteService campsiteService;

    @Autowired
    MemberRepository memberRepository;


    @Test
    @DisplayName("dto 생성시 scrap 양방향 접근 정상 작동 확인")
    @Transactional
    public void ScrapTest (){

        Campsite campsite = Campsite.builder().campName("test").uuid(UUID.randomUUID()).build();
        Campsite savedCampsite = campsiteRepository.save(campsite);

        Member member = Member.builder().name("lmj").build();
        Member savedMember = memberRepository.save(member);


        List<CampsiteListResDTO> result1 = campsiteService.getCampsiteListByFiltering("test", "", new String[0], new String[0],
                new String[0], new String[0], new String[0], new String[0], new String[0], savedMember.getId());

        Assertions.assertThat(result1.get(0).getIsScraped()).isEqualTo(false);


        campsiteService.scrap(savedMember.getId(), campsite.getUuid());

        List<CampsiteListResDTO> result2 = campsiteService.getCampsiteListByFiltering("test", "", new String[0], new String[0],
                new String[0], new String[0], new String[0], new String[0], new String[0], savedMember.getId());

        Assertions.assertThat(result2.get(0).getIsScraped()).isEqualTo(true);


        campsiteService.scrap(savedMember.getId(), campsite.getUuid());

        List<CampsiteListResDTO> result3 = campsiteService.getCampsiteListByFiltering("test", "", new String[0], new String[0],
                new String[0], new String[0], new String[0], new String[0], new String[0], savedMember.getId());

        Assertions.assertThat(result3.get(0).getIsScraped()).isEqualTo(false);

    }


}
