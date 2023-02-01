package com.ssafy.campinity.core.cascade;

import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.entity.message.Message;
import com.ssafy.campinity.core.repository.campsite.CampsiteRepository;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import com.ssafy.campinity.core.repository.message.MessageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
public class PersistenceCasCadeTest {

    @Autowired
    CampsiteRepository campsiteRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MessageRepository messageRepository;

    /**
    * merge cascade test
    * 영속 상태의 하위 객체에 데이터 변경 사항 업데이트
     **/
    @Test
    @DisplayName("MERGE 영속성 전이 테스트")
    void mergeCascadeTest(){

        Campsite campsite = campsiteRepository.findById(1).orElseThrow();

        Member member = Member.builder()
                .email("test@Tset.com").name("test").profileImage("")
                .build();

        Member savedMember = memberRepository.save(member);

        UUID uuid = UUID.randomUUID();

        Message message = Message.builder()
                .uuid(uuid)
                .content("message content")
                .longitude(111.0)
                .latitude(111.0)
                .member(savedMember)
                .campsite(campsite)
                .messageCategory("자유")
                .build();

//        savedMember.getMessages().add(message);
        messageRepository.save(message);

        Message savedMessage = messageRepository.findByUuidAndExpiredIsFalse(uuid).get();
        Member testMember = memberRepository.findMemberByEmail("test@Tset.com").get();

        assertEquals(savedMessage.getMember().getId(), testMember.getId());
    }

    /**
     * persist cascade test
     * transient 상태의 하위 관계 객체에 변경 정보 업데이트되는지 테스트
     **/
    @Test
    @DisplayName("PERSIST 영속성 전이 테스트")
    void pesistCascadeTest(){

        Campsite campsite = campsiteRepository.findById(1).orElseThrow();

        Member member = Member.builder()
                .email("test@Tset.com").name("test").profileImage("")
                .build();

        UUID uuid = UUID.randomUUID();

        Message message = Message.builder()
                .uuid(uuid)
                .content("message content")
                .longitude(111.0)
                .latitude(111.0)
                .member(member)
                .campsite(campsite)
                .messageCategory("자유")
                .build();


        Message savedMessage = messageRepository.save(message);

        assertEquals(savedMessage.getMember().getId(), member.getId());
    }
}
