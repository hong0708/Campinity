package com.ssafy.campinity.core.service;

import com.ssafy.campinity.core.dto.CampsiteListResDTO;
import com.ssafy.campinity.core.dto.MessageReqDTO;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.campsite.CampsiteAndIndustry;
import com.ssafy.campinity.core.entity.campsite.Industry;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.entity.message.Message;
import com.ssafy.campinity.core.repository.campsite.*;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.util.ArrayList;
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

    @Autowired
    MessageService messageService;

    @Autowired
    IndustryRepository industryRepository;
    @Autowired
    CampsiteAndIndustryRepository campsiteAndIndustryRepository;


    @Test
    @DisplayName("dto 생성시 scrap 양방향 접근 정상 작동 확인")
    @Transactional
    public void ScrapTest (){

        Campsite campsite = Campsite.builder().campName("test").uuid(UUID.randomUUID()).messages(new ArrayList<>()).build();
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

    @Test
    @DisplayName("dto 생성시 메세지 양방향 접근 정상 작동 확인")
    @Transactional
    public void MessageTest () throws FileNotFoundException {

        Campsite campsite = Campsite.builder().campName("test").uuid(UUID.randomUUID()).messages(new ArrayList<>()).build();
        Campsite savedCampsite = campsiteRepository.save(campsite);

        Member member = Member.builder().name("lmj").build();
        Member savedMember = memberRepository.save(member);


        List<CampsiteListResDTO> result1 = campsiteService.getCampsiteListByFiltering("test", "", new String[0], new String[0],
                new String[0], new String[0], new String[0], new String[0], new String[0], savedMember.getId());
        Assertions.assertThat(result1.get(0).getMessageCnt()).isEqualTo(0);


        Message savedMessage = messageService.createMessage(MessageReqDTO.builder().messageCategory("리뷰").campsiteId(campsite.getUuid().toString()).build(), savedMember.getId());
        List<CampsiteListResDTO> result2 = campsiteService.getCampsiteListByFiltering("test", "", new String[0], new String[0],
                new String[0], new String[0], new String[0], new String[0], new String[0], savedMember.getId());
        Assertions.assertThat(result2.get(0).getMessageCnt()).isEqualTo(1);


        messageService.deleteMessage(savedMessage.getUuid().toString(),savedMember.getId());
        List<CampsiteListResDTO> result3 = campsiteService.getCampsiteListByFiltering("test", "", new String[0], new String[0],
                new String[0], new String[0], new String[0], new String[0], new String[0], savedMember.getId());
        Assertions.assertThat(result3.get(0).getMessageCnt()).isEqualTo(0);
    }

    @Test
    @DisplayName("keyword filtering이 제대로 작동하는지 test")
    @Transactional
    public void filteringKeywordTest () {

        Campsite campsite1 = Campsite.builder().campName("1test2").uuid(UUID.randomUUID()).messages(new ArrayList<>()).build();
        Campsite savedCampsite1 = campsiteRepository.save(campsite1);

        Campsite campsite2 = Campsite.builder().campName("campinity").uuid(UUID.randomUUID()).messages(new ArrayList<>()).build();
        Campsite savedCampsite2 = campsiteRepository.save(campsite2);

        Campsite campsite3 = Campsite.builder().campName("test").uuid(UUID.randomUUID()).messages(new ArrayList<>()).build();
        Campsite savedCampsite3 = campsiteRepository.save(campsite3);

        Member member = Member.builder().name("lmj").build();
        Member savedMember = memberRepository.save(member);

        List<CampsiteListResDTO> result1 = campsiteService.getCampsiteListByFiltering("test", "", new String[0], new String[0],
                new String[0], new String[0], new String[0], new String[0], new String[0], savedMember.getId());

        Assertions.assertThat(result1.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("keyword doName 제대로 작동하는지 test")
    @Transactional
    public void filteringdoNameTest () {

        Campsite campsite1 = Campsite.builder().doName("싸피군").uuid(UUID.randomUUID()).messages(new ArrayList<>()).build();
        Campsite savedCampsite1 = campsiteRepository.save(campsite1);

        Campsite campsite2 = Campsite.builder().doName("싸피시").uuid(UUID.randomUUID()).messages(new ArrayList<>()).build();
        Campsite savedCampsite2 = campsiteRepository.save(campsite2);

        Campsite campsite3 = Campsite.builder().doName("삼성시").uuid(UUID.randomUUID()).messages(new ArrayList<>()).build();
        Campsite savedCampsite3 = campsiteRepository.save(campsite3);

        Member member = Member.builder().name("lmj").build();
        Member savedMember = memberRepository.save(member);

        List<CampsiteListResDTO> result1 = campsiteService.getCampsiteListByFiltering("", "싸피군 ", new String[0], new String[0],
                new String[0], new String[0], new String[0], new String[0], new String[0], savedMember.getId());

        Assertions.assertThat(result1.size()).isEqualTo(1);
        Assertions.assertThat(result1.get(0).getDoName()).isEqualTo("싸피군");
    }

    @Test
    @DisplayName("keyword sigungu 제대로 작동하는지 test")
    @Transactional
    public void filteringSigunguTest () {

        Campsite campsite1 = Campsite.builder().sigunguName("싸피군").uuid(UUID.randomUUID()).messages(new ArrayList<>()).build();
        Campsite savedCampsite1 = campsiteRepository.save(campsite1);

        Campsite campsite2 = Campsite.builder().sigunguName("싸피시").uuid(UUID.randomUUID()).messages(new ArrayList<>()).build();
        Campsite savedCampsite2 = campsiteRepository.save(campsite2);

        Campsite campsite3 = Campsite.builder().sigunguName("삼성시").uuid(UUID.randomUUID()).messages(new ArrayList<>()).build();
        Campsite savedCampsite3 = campsiteRepository.save(campsite3);

        Member member = Member.builder().name("lmj").build();
        Member savedMember = memberRepository.save(member);

        String[] conditions = {"싸피군", "싸피시"};

        List<CampsiteListResDTO> result1 = campsiteService.getCampsiteListByFiltering("", "", conditions, new String[0],
                new String[0], new String[0], new String[0], new String[0], new String[0], savedMember.getId());

        Assertions.assertThat(result1.size()).isEqualTo(2);
    }
//
//    @Test
//    @DisplayName("keyword Industry 제대로 작동하는지 test")
//    @Transactional
//    public void filteringIndustryTest () {
//
//        Industry industry1 = Industry.builder().industryName("test1").build();
//        Industry industry2 = Industry.builder().industryName("test2").build();
//        Industry industry3 = Industry.builder().industryName("test3").build();
//        Industry savedIndusty1 = industryRepository.save(industry1);
//        Industry savedIndusty2 = industryRepository.save(industry2);
//        Industry savedIndusty3 = industryRepository.save(industry3);
//
//        Campsite campsite1 = Campsite.builder().sigunguName("싸피군").uuid(UUID.randomUUID()).messages(new ArrayList<>()).build();
//        Campsite savedCampsite1 = campsiteRepository.save(campsite1);
//
//        Campsite campsite2 = Campsite.builder().sigunguName("싸피시").uuid(UUID.randomUUID()).messages(new ArrayList<>()).build();
//        Campsite savedCampsite2 = campsiteRepository.save(campsite2);
//
//        Campsite campsite3 = Campsite.builder().sigunguName("삼성시").uuid(UUID.randomUUID()).messages(new ArrayList<>()).build();
//        Campsite savedCampsite3 = campsiteRepository.save(campsite3);
//
//        Member member = Member.builder().name("lmj").build();
//        Member savedMember = memberRepository.save(member);
//
//        CampsiteAndIndustry campsiteAndIndustry1 = CampsiteAndIndustry.builder().industry(savedIndusty1).campsite(savedCampsite1).build();
//        CampsiteAndIndustry campsiteAndIndustry2 = CampsiteAndIndustry.builder().industry(savedIndusty2).campsite(savedCampsite1).build();
//
//        CampsiteAndIndustry campsiteAndIndustry3 = CampsiteAndIndustry.builder().industry(savedIndusty2).campsite(savedCampsite2).build();
//        CampsiteAndIndustry campsiteAndIndustry4 = CampsiteAndIndustry.builder().industry(savedIndusty3).campsite(savedCampsite2).build();
//
//        CampsiteAndIndustry campsiteAndIndustry5 = CampsiteAndIndustry.builder().industry(savedIndusty3).campsite(savedCampsite3).build();
//
//        CampsiteAndIndustry savedCamsiteAndIndustry1 = campsiteAndIndustryRepository.save(campsiteAndIndustry1);
//        CampsiteAndIndustry savedCamsiteAndIndustry2 = campsiteAndIndustryRepository.save(campsiteAndIndustry2);
//        CampsiteAndIndustry savedCamsiteAndIndustry3 = campsiteAndIndustryRepository.save(campsiteAndIndustry3);
//        CampsiteAndIndustry savedCamsiteAndIndustry4 = campsiteAndIndustryRepository.save(campsiteAndIndustry4);
//        CampsiteAndIndustry savedCamsiteAndIndustry5 = campsiteAndIndustryRepository.save(campsiteAndIndustry5);
//
//
//        String[] conditions = {Integer.toString(savedIndusty1.getId()), Integer.toString(savedIndusty2.getId())};
//
//        List<CampsiteListResDTO> result1 = campsiteService.getCampsiteListByFiltering("", "", new String[0], new String[0],
//                new String[0], conditions, new String[0], new String[0], new String[0], savedMember.getId());
//
//        Assertions.assertThat(result1.size()).isEqualTo(2);
//    }


}
