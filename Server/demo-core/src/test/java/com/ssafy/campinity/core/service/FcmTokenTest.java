package com.ssafy.campinity.core.service;


import com.ssafy.campinity.core.entity.fcmToken.FcmToken;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.repository.fcmToken.FcmTokenRepository;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class FcmTokenTest {


    @Autowired
    FcmTokenRepository fcmTokenRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void FcmTokenPersistTest(){

        Member member = Member.builder()
                .email("test@Tset.com").name("test").profileImage("")
                .build();
        Member savedMember = memberRepository.save(member);

        FcmToken fcmToken = FcmToken.builder().fcmToken(UUID.randomUUID().toString()).member(member).build();
        member.setFcmToken(fcmToken);
        Member testMember = memberRepository.save(member);

        FcmToken persistedFcmToken = fcmTokenRepository.findByMember_Id(testMember.getId()).orElse(null);

        assertNotNull(persistedFcmToken);
        assertEquals(persistedFcmToken.getFcmToken(), fcmToken.getFcmToken());
    }

}
