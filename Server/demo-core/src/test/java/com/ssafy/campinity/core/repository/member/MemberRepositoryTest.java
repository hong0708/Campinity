package com.ssafy.campinity.core.repository.member;

import com.ssafy.campinity.core.entity.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;


//@Transactional
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Test
    void UserTest(){
        Member member = Member.builder()
                .uuid(UUID.randomUUID())
                .email("sss@sss.sss")
                .name("이원일")
                .build();
        memberRepository.save(member);
    }

}