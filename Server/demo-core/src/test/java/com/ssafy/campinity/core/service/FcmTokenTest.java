package com.ssafy.campinity.core.service;


import com.ssafy.campinity.core.dto.FcmTokenResDTO;
import com.ssafy.campinity.core.entity.fcm.FcmToken;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.repository.fcm.FcmTokenRepository;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@Transactional
@SpringBootTest
public class FcmTokenTest {


    @Autowired
    FcmTokenRepository fcmTokenRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    FcmMessageService fcmMessageService;
    @Autowired
    FcmTokenService fcmTokenService;

    /**
     * fcmTokenService.saveFcmToken에 따라 토큰 갱신 여부 확인
     * 1. expiredDate 갱신 여부 확인
     * 2. 다른 맴버가 동일한 기기로 토큰을 저장할 시 기존 토큰 삭제 및 요청 맴버로 token 저장
     */
    @Test
    @DisplayName("fcm 토큰 갱신 ")
    void refreshFcmTokenTest(){
        Member member = Member.builder()
                .email("test@Tset.com").name("test").profileImage("")
                .build();
        Member savedMember = memberRepository.save(member);

        String uuid = UUID.randomUUID().toString();
        LocalDate testTime = LocalDate.now();

        savedMember.addFcmToken(
                FcmToken.builder()
                        .token(uuid)
                        .member(savedMember)
                        .expiredDate(testTime).build());
        Member testMember = memberRepository.save(savedMember);

        FcmTokenResDTO fcmTokenResDTO = fcmTokenService.saveFcmToken(testMember.getId(), uuid);

        assertNotEquals(fcmTokenResDTO.getExpiredDate(), testTime.toString());

        Member member2 = Member.builder()
                .email("test2@Tset2.com").name("test2").profileImage("")
                .build();
        Member savedMember2 = memberRepository.save(member2);

        FcmTokenResDTO fcmTokenResDTO2 = fcmTokenService.saveFcmToken(savedMember2.getId(), uuid);
        FcmToken fcmTokenTest = fcmTokenRepository.findByMember_Id(testMember.getId()).orElse(null);

        Member testMember2 = memberRepository.findMemberByEmail("test2@Tset2.com").orElse(null);

        assertNull(fcmTokenTest);
        assertEquals(testMember2.getFcmTokenList().get(0).getMember().getId(), testMember2.getId());
        assertEquals(testMember2.getFcmTokenList().get(0).getToken(), uuid);
    }

    /**
     * 유저 특정 기기 fcm 토큰 삭제 요청
     * 토큰 리스트 중 요청 들어온 특정 토큰만 삭제
     */
    @Test
    @DisplayName("유저 특정 기기 fcm 토큰 삭제 요청")
    void Test(){

        Member member = Member.builder()
                .email("test@Tset.com").name("test").profileImage("")
                .build();
        Member savedMember = memberRepository.save(member);

        String token1 = UUID.randomUUID().toString();
        String token2 = UUID.randomUUID().toString();

        FcmTokenResDTO fcmTokenResDTO1 = fcmTokenService.saveFcmToken(savedMember.getId(), token1);
        FcmTokenResDTO fcmTokenResDTO2 = fcmTokenService.saveFcmToken(savedMember.getId(), token2);

        List<FcmToken> fcmTokenList = fcmTokenRepository.findAllByMember_Id(savedMember.getId());
        assertEquals(2, fcmTokenList.size());

        FcmToken fcmToken = fcmTokenRepository.findByToken(token1).get();
        member.removeFcmToken(fcmToken);
        fcmTokenRepository.deleteByToken(token1);

        List<FcmToken> testfcmTokenList = fcmTokenRepository.findAllByMember_Id(savedMember.getId());
        assertEquals(1, testfcmTokenList.size());
        assertEquals(token2, testfcmTokenList.get(0).getToken());
    }
    @Test
    void FcmTokenPersistTest(){

        Member member = Member.builder()
                .email("test@Tset.com").name("test").profileImage("")
                .build();
        Member savedMember = memberRepository.save(member);

        String uuid = UUID.randomUUID().toString();

        savedMember.addFcmToken(FcmToken.builder().token(uuid).member(savedMember).build());
        Member testMember = memberRepository.save(savedMember);

        FcmToken persistedFcmToken = fcmTokenRepository.findByMember_Id(testMember.getId()).orElse(null);
        persistedFcmToken.refreshFcmToken(uuid);

        FcmToken updatedfcmToken = fcmTokenRepository.save(persistedFcmToken);
        System.out.println(updatedfcmToken.toString());
        assertNotNull(persistedFcmToken);
        assertEquals(persistedFcmToken.getToken(), testMember.getFcmTokenList().get(0));
        assertEquals(persistedFcmToken.getMember().getId(), testMember.getId());
    }



    /**
     * subscribeCamp 메서드 테스트
     * 1. 캠핑장 구독 / 취소 과정
     * 2. 다른 유저가 동일한 토큰으로 구독 시 기존 토큰 삭제 및 요청 유저에 할당
     * 3. 기존에 없는 토큰 요청 시 생성 후 구독
     */
    @Test
    @DisplayName("특정 캠핑장 알람 신청 및 해제 기능 테스트")
    void subscribeCampTest(){

        Member member = Member.builder()
                .email("test@Tset.com").name("test").profileImage("")
                .build();
        Member savedMember = memberRepository.save(member);

        String uuid = UUID.randomUUID().toString();
        FcmToken fcmToken = FcmToken.builder().token(uuid).member(savedMember).expiredDate(LocalDate.now().plusMonths(1)).build();
        savedMember.addFcmToken(fcmToken);
        FcmToken savedFcmToken = fcmTokenRepository.save(fcmToken);
        // 1
        FcmTokenResDTO subscribeCampToken = fcmTokenService.subscribeCamp("Camp", savedMember.getId(), uuid);
        assertEquals("Camp", subscribeCampToken.getSubscribeCampId());

        FcmTokenResDTO unsubscribeCampToken = fcmTokenService.subscribeCamp("", savedMember.getId(), uuid);
        assertEquals("", unsubscribeCampToken.getSubscribeCampId());

        FcmTokenResDTO resubscribeCampToken = fcmTokenService.subscribeCamp("Camp", savedMember.getId(), uuid);
        assertEquals("Camp", resubscribeCampToken.getSubscribeCampId());

        // 2
        Member otherMember = Member.builder()
                .email("other@other.com").name("other").profileImage("")
                .build();
        Member savedOther = memberRepository.save(otherMember);

        FcmTokenResDTO subscribeCampOther = fcmTokenService.subscribeCamp("CampOther", savedOther.getId(), uuid);
        assertEquals("CampOther", subscribeCampOther.getSubscribeCampId());
        assertEquals(1, fcmTokenRepository.findByToken(uuid).stream().count());
        assertEquals(savedOther.getId(), fcmTokenRepository.findByToken(uuid).get().getMember().getId());

        // 3
        Member newMember = Member.builder()
                .email("other@other.com").name("other").profileImage("")
                .build();
        Member savedNew = memberRepository.save(newMember);
        String newToken = UUID.randomUUID().toString();
        FcmTokenResDTO subscribeCampNew = fcmTokenService.subscribeCamp("CampNew", savedNew.getId(), newToken);
        assertEquals("CampNew", subscribeCampNew.getSubscribeCampId());
        assertEquals(1, fcmTokenRepository.findByToken(newToken).stream().count());
        assertEquals(savedNew.getId(), fcmTokenRepository.findByToken(newToken).get().getMember().getId());

    }

    /**
     * deleteFcmToken 테스트
     * 1. 요청 맴버가 가지고 있는 토큰 삭제
     */
    @Test
    @DisplayName("토큰 삭제 기능 테스트")
    void deleteTokenTest(){
        Member member = Member.builder()
                .email("test@Tset.com").name("test").profileImage("")
                .build();
        Member savedMember = memberRepository.save(member);

        String uuid = UUID.randomUUID().toString();
        FcmToken fcmToken = FcmToken.builder().token(uuid).member(savedMember).expiredDate(LocalDate.now().plusMonths(1)).build();
        savedMember.addFcmToken(fcmToken);
        FcmToken savedFcmToken = fcmTokenRepository.save(fcmToken);

        fcmTokenService.deleteFcmToken(savedFcmToken.getMember().getId(), savedFcmToken.getToken());

        FcmToken deletedToken = fcmTokenRepository.findByToken(uuid).orElse(null);
        assertEquals(null, deletedToken);
    }
    
    @Test
    void findMyFcmTokenTest(){

        Member member1 = Member.builder()
                .email("test@Tset.com").name("test").profileImage("")
                .build();
        Member savedMember = memberRepository.save(member1);

        String token1 = UUID.randomUUID().toString();
        FcmToken fcmToken = FcmToken.builder().token(token1).member(savedMember).expiredDate(LocalDate.now().plusMonths(1)).build();
        savedMember.addFcmToken(fcmToken);
        FcmToken savedFcmToken = fcmTokenRepository.save(fcmToken);
    
        Member member2 = Member.builder()
                .email("test@Tset.com").name("test").profileImage("")
                .build();
        Member savedMember2 = memberRepository.save(member2);

        String token2 = UUID.randomUUID().toString();
        FcmToken fcmToken2 = FcmToken.builder().token(token2).member(savedMember2).expiredDate(LocalDate.now().plusMonths(1)).build();
        savedMember2.addFcmToken(fcmToken2);
        FcmToken savedFcmToken2 = fcmTokenRepository.save(fcmToken);

        FcmTokenResDTO res = fcmTokenService.findMyFcmToken(savedMember.getId(), token1);
        FcmTokenResDTO res1 = fcmTokenService.findMyFcmToken(savedMember.getId(), token2);
        assertNotNull(res);
        assertNotNull(res1);


    }

//    @Test
//    @DisplayName("fcm 전송 테스트")
//    void sendFcmMessageTest() throws IOException {
//
//        fcmMessageService.sendMessageToMany("e_siNmW0RaGYsPL7rWutNq:APA91bFVVGRIB5PSBqoAJlnGGBgWXIcNG_uLDVhoeKU3mJy5BNxWqxV-jWhCuQQlncXVZKOLlY06GwY2kQUh0oC8IlSXweaGRVBwWtanDMCLI9VUz0zOoelW-tg7BazCP3LvbEEaTRwH",
//                "test",
//                "testbody");
//
//    }
}
