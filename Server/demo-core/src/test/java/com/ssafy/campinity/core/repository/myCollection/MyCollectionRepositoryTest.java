package com.ssafy.campinity.core.repository.myCollection;


import com.ssafy.campinity.core.dto.MyCollectionResDTO;
import com.ssafy.campinity.core.entity.MyCollection.MyCollection;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import com.ssafy.campinity.core.service.MyCollectionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class MyCollectionRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MyCollectionRepository myCollectionRepository;
    @Autowired
    MyCollectionService myCollectionService;

    @Test
    @DisplayName("컬렉션 최신순 조회 테스트")
    void LatestCollectionSearchTest() throws InterruptedException {

        Member member = Member.builder()
                .email("test@Tset.com").name("test").profileImage("")
                .build();
        Member savedMember = memberRepository.save(member);

        MyCollection myCollection = MyCollection.builder()
                .uuid(UUID.randomUUID())
                .campsiteName("첫번째 생성")
                .member(savedMember)
                .date("1234")
                .build();
        myCollectionRepository.save(myCollection);

        MyCollection myCollection1 = MyCollection.builder()
                .uuid(UUID.randomUUID())
                .campsiteName("두번째 생성")
                .member(savedMember)
                .date("1234")
                .build();
        myCollectionRepository.save(myCollection1);

        MyCollection myCollection2 = MyCollection.builder()
                .uuid(UUID.randomUUID())
                .campsiteName("세번째 생성")
                .member(savedMember)
                .date("1234")
                .build();
        myCollectionRepository.save(myCollection2);

        MyCollection myCollection3 = MyCollection.builder()
                .uuid(UUID.randomUUID())
                .campsiteName("네번째 생성")
                .member(savedMember)
                .date("1234")
                .build();
        myCollectionRepository.save(myCollection3);

        MyCollection myCollection4 = MyCollection.builder()
                .uuid(UUID.randomUUID())
                .campsiteName("다섯번째 생성")
                .member(savedMember)
                .date("1234")
                .build();
        myCollectionRepository.save(myCollection4);

        MyCollection myCollection5 = MyCollection.builder()
                .uuid(UUID.randomUUID())
                .campsiteName("여섯번째 생성")
                .member(savedMember)
                .date("1234")
                .build();
        myCollectionRepository.save(myCollection5);

        List<MyCollection> myCollectionList = myCollectionService.getLatestMyCollections(savedMember.getId());

        assertTrue(myCollectionList.get(0).getUpdatedAt().isAfter(myCollectionList.get(1).getUpdatedAt()));
        assertEquals(5, myCollectionList.size());
    }
}
