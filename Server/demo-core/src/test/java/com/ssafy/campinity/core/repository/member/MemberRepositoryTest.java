package com.ssafy.campinity.core.repository.member;

import com.ssafy.campinity.core.entity.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Test
    void UserTest(){
        Member member = Member.builder()
                .uuid(UUID.randomUUID())
                .email("sss@sss.sss")
                .name("test")
                .build();
        memberRepository.save(member);
    }

}