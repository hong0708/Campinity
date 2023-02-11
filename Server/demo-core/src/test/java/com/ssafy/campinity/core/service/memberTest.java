package com.ssafy.campinity.core.service;

import com.ssafy.campinity.core.dto.MemberResDTO;
import com.ssafy.campinity.core.dto.ProfileResDTO;
import com.ssafy.campinity.core.entity.fcm.FcmToken;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.List;


@Transactional
@SpringBootTest
public class memberTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("유저 프로필 조회 테스트")
    void getMemberProfileTest(){
        Member member = Member.builder().uuid(UUID.randomUUID())
                .name("test")
                .profileImage("/images/member/test.png")
                .email("Test@test.com").build();

        FcmToken fcmToken1 = FcmToken.builder().token("token1").campsiteUuid("campId1").build();
        FcmToken fcmToken2 = FcmToken.builder().token("token2").campsiteUuid("campId2").build();

        member.addFcmToken(fcmToken1);
        member.addFcmToken(fcmToken2);
        Member member1 = memberRepository.save(member);

        ProfileResDTO profileResDTO = memberService.getMemberProfile(member1.getId());
        System.out.println(profileResDTO.toString());
    }
}
