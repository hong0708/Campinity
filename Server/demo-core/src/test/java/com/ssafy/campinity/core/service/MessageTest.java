package com.ssafy.campinity.core.service;


import com.ssafy.campinity.core.dto.MessageReqDTO;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.entity.message.LikeMessage;
import com.ssafy.campinity.core.entity.message.Message;
import com.ssafy.campinity.core.repository.campsite.CampsiteRepository;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import com.ssafy.campinity.core.repository.message.LikeMessageRepository;
import com.ssafy.campinity.core.repository.message.MessageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.campinity.core.entity.message.MessageCategory.ETC;
import static com.ssafy.campinity.core.entity.message.MessageCategory.REVIEW;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class MessageTest {

    @Autowired
    CampsiteRepository campsiteRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    MessageService messageService;
    @Autowired
    LikeMessageRepository likeMessageRepository;

    @Test
    @DisplayName("message 작성 테스트")
    void postMessageTest(){

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

        messageRepository.save(message);

        Message savedMessage = messageRepository.findByUuidAndExpiredIsFalse(uuid).get();
        Member testMember = memberRepository.findMemberByEmail("test@Tset.com").get();

        assertEquals(savedMessage.getMember().getId(), testMember.getId());
    }

    /**
     * message 참조 객체 저장 service test
     * 1. campsite, member 생성 & 저장
     * 2. MessageReqDTO 생성. member 객체 주입
     * 3. messageService.createMessage service 실행
     * 4. message에 주입된 member id가 동일한지 주입한 member 객체의 id와 동일한지 확인
     */
    @Test
    @DisplayName("쪽지 작성 service 테스트")
    void postMessageServiceTest(){

        Campsite campsite = Campsite.builder()
                .address("전남 담양군 봉산면 탄금길 9-26")
                .allowAnimal("불가능")
                .campName("힐포인트")
                .contentId(125423)
                .dayOperation("평일, 주말")
                .doName("충청남도")
                .latitude(36.8822361)
                .longitude(130.8338106)
                .sigunguName("구미시")
                .uuid(UUID.randomUUID())
                .build();

        Campsite savedCampsite = campsiteRepository.save(campsite);

        Member member = Member.builder()
                .email("test@Tset.com").name("test").profileImage("")
                .build();

        Member savedMember = memberRepository.save(member);

        MessageReqDTO request = MessageReqDTO.builder()
                .campsiteId(savedCampsite.getUuid().toString())
                .messageCategory("자유")
                .content("testet")
                .longitude(111.0)
                .latitude(111.0)
                .file(implementFile())
                .build();

        Message postedMessage = messageService.createMessage(request, savedMember.getId());
        assertEquals(postedMessage.getMember().getId(), savedMember.getId());
    }

    /**
     * likeMessage cascade test
     * 1. message 생성(campsite, member 관계 주입)
     * 2. member 생성 & messageService.likeMessages
     * 3. message - likeMessage service 실행
     *      - messageService.likeMessage 내에서 message에 likeMessage 정보 주입
     *      - likeMessage 객체 저장
     * 4. likeMessage 조회 후 message에 persist Cascade 진행됐는지 확인
     * 5. 2 ~ 4 순을 반복하여 delete cascade 확인
     */
    @Test
    @DisplayName("쪽지 좋아요 기능 영속성 전이 테스트")
    void likeMessageCascadeTest(){

        Message message = createMessage();

        Member member = Member.builder()
                .email("test1@test1.com").name("test1").profileImage("")
                .build();
        Member savedMember = memberRepository.save(member);

        boolean likeCheck = messageService.likeMessage(savedMember.getId(), message.getUuid().toString()); // 좋아요
        if (likeCheck) {
            LikeMessage likeMessage = likeMessageRepository.findByMemberAndMessage(savedMember, message).orElseThrow();
            assertEquals(likeMessage.getMessage().getId(), message.getLikeMessages().get(0).getMessage().getId());
        }

        boolean likeCheck1 = messageService.likeMessage(savedMember.getId(), message.getUuid().toString()); // 좋아요 취소

        if (!likeCheck1){
            LikeMessage likeMessage = likeMessageRepository.findByMemberAndMessage(savedMember, message).orElse(null);

            assertNull(likeMessage);
            assertEquals(0, message.getLikeMessages().size());
        }
    }

    /**
     * @delete-message-cascade-Test
     *
     * Message_likeMessage에 cascadeType.REMOVE 설정
     *  - 종속적 관계이기 때문
     * message 삭제 시 주인 테이블인 likeMessage 객체 삭제
     */
    @Test
    void deleteMessageCascadeTest() throws FileNotFoundException {

        Campsite campsite = Campsite.builder()
                .address("전남 담양군 봉산면 탄금길 9-26")
                .allowAnimal("불가능")
                .campName("힐포인트")
                .contentId(125423)
                .dayOperation("평일, 주말")
                .doName("충청남도")
                .latitude(36.8822361)
                .longitude(130.8338106)
                .sigunguName("구미시")
                .uuid(UUID.randomUUID())
                .build();

        Campsite savedCampsite = campsiteRepository.save(campsite);

        Member member = Member.builder()
                .email("test@Tset.com").name("test").profileImage("")
                .build();

        Member savedMember = memberRepository.save(member);

        MessageReqDTO request = MessageReqDTO.builder()
                .campsiteId(savedCampsite.getUuid().toString())
                .messageCategory("자유")
                .content("testet")
                .longitude(111.0)
                .latitude(111.0)
                .file(implementFile())
                .build();

        Message message = messageService.createMessage(request, savedMember.getId());

        Member member1 = Member.builder()
                .email("test1@test1.com").name("test1").profileImage("")
                .build();
        Member member2 = memberRepository.save(member1);

        boolean likeCheck = messageService.likeMessage(member2.getId(), message.getUuid().toString());

        messageService.deleteMessage(message.getUuid().toString(), savedMember.getId());

        List<LikeMessage> likeMessages = likeMessageRepository.findByMember(savedMember);
        assertEquals(0, likeMessages.size());
    }

    @Test
    void getMyMessageTest(){
        Campsite campsite = Campsite.builder()
                .address("전남 담양군 봉산면 탄금길 9-26")
                .allowAnimal("불가능")
                .campName("힐포인트")
                .contentId(125423)
                .dayOperation("평일, 주말")
                .doName("충청남도")
                .latitude(36.8822361)
                .longitude(130.8338106)
                .sigunguName("구미시")
                .uuid(UUID.randomUUID())
                .build();

        Campsite savedCampsite = campsiteRepository.save(campsite);

        Member member = Member.builder()
                .email("test@Tset.com").name("test").profileImage("")
                .build();

        Member savedMember = memberRepository.save(member);

        MessageReqDTO request = MessageReqDTO.builder()
                .campsiteId(savedCampsite.getUuid().toString())
                .messageCategory("자유")
                .content("testet")
                .longitude(111.0)
                .latitude(111.0)
                .file(implementFile())
                .build();

        MessageReqDTO request1 = MessageReqDTO.builder()
                .campsiteId(savedCampsite.getUuid().toString())
                .messageCategory("리뷰")
                .content("testet")
                .longitude(111.0)
                .latitude(111.0)
                .file(implementFile())
                .build();

        messageService.createMessage(request, savedMember.getId());

        messageService.createMessage(request1, savedMember.getId());

        List<Message> myMessages = messageService.getMyMessages(savedMember.getId());

        assertEquals(1, myMessages.stream().filter(message -> message.getMessageCategory().equals(ETC)).collect(Collectors.toList()).size());
        assertEquals(1, myMessages.stream().filter(message -> message.getMessageCategory().equals(REVIEW)).collect(Collectors.toList()).size());

    }


    // 빈 파일 생성 메서드
    public MultipartFile implementFile(){
        return new MultipartFile() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getOriginalFilename() {
                return null;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {

            }
        };
    }

    // 테스트 통과한 code 기반 쪽지 객체 생성 method
    public Message createMessage() {

        Campsite campsite = Campsite.builder()
                .address("전남 담양군 봉산면 탄금길 9-26")
                .allowAnimal("불가능")
                .campName("힐포인트")
                .contentId(125423)
                .dayOperation("평일, 주말")
                .doName("충청남도")
                .latitude(36.8822361)
                .longitude(130.8338106)
                .sigunguName("구미시")
                .uuid(UUID.randomUUID())
                .build();

        Campsite savedCampsite = campsiteRepository.save(campsite);

        Member member = Member.builder()
                .email("test@Tset.com").name("test").profileImage("")
                .build();

        Member savedMember = memberRepository.save(member);

        MessageReqDTO request = MessageReqDTO.builder()
                .campsiteId(savedCampsite.getUuid().toString())
                .messageCategory("자유")
                .content("testet")
                .longitude(111.0)
                .latitude(111.0)
                .file(implementFile())
                .build();

        return messageService.createMessage(request, savedMember.getId());
    }
}
