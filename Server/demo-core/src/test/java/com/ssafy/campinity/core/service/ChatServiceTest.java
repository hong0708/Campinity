package com.ssafy.campinity.core.service;

import com.ssafy.campinity.core.dto.MyChatRoomResDTO;
import com.ssafy.campinity.core.entity.chat.ChatRoom;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.repository.chatting.ChatMessageRepository;
import com.ssafy.campinity.core.repository.chatting.ChatRoomRepository;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
public class ChatServiceTest {

    @Autowired
    ChatService chatService;

    @Autowired
    ChatRoomRepository chatRoomRepository;

    @Autowired
    ChatMessageRepository chatMessageRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("내 채팅방 조회 테스트")
    void ChatServiceTest(){
        Member member1 = Member.builder()
                .email("chat1@chat1.com").name("chat1").profileImage("member1.png").uuid(UUID.randomUUID())
                .build();
        Member savedMember1 = memberRepository.save(member1);
        Member member2 = Member.builder()
                .email("chat2@chat2.com").name("chat2").profileImage("member2.png").uuid(UUID.randomUUID())
                .build();
        Member savedMember2 = memberRepository.save(member2);
        Member member3 = Member.builder()
                .email("chat3@chat3.com").name("chat3").profileImage("member3.png").uuid(UUID.randomUUID())
                .build();
        Member savedMember3 = memberRepository.save(member3);
        Member member4 = Member.builder()
                .email("chat4@chat4.com").name("chat4").profileImage("member4.png").uuid(UUID.randomUUID())
                .build();
        Member savedMember4 = memberRepository.save(member4);

        String uuid1 = UUID.randomUUID().toString();
        LocalDateTime localDate1 = LocalDateTime.now().minusDays(3);
        String uuid2 = UUID.randomUUID().toString();
        LocalDateTime localDate2 = LocalDateTime.now().minusDays(1);
        String uuid3 = UUID.randomUUID().toString();
        LocalDateTime localDate3 = LocalDateTime.now().minusDays(2);

        ChatRoom chatRoom1 = ChatRoom.builder()
                .id(uuid1)
                .fcmMessageBody("body1")
                .createdAt(localDate1)
                .users(List.of(savedMember1.getUuid().toString(), savedMember2.getUuid().toString()))
                .name("캠핑장에서 누구와 대화")
                .build();
        ChatRoom chatRoom2 = ChatRoom.builder()
                .id(uuid2)
                .fcmMessageBody("body2")
                .createdAt(localDate2)
                .users(List.of(savedMember1.getUuid().toString(), savedMember3.getUuid().toString()))
                .name("캠핑장에서 누구와 대화")
                .build();
        ChatRoom chatRoom3 = ChatRoom.builder()
                .id(uuid3)
                .fcmMessageBody("body3")
                .createdAt(localDate3)
                .users(List.of(savedMember2.getUuid().toString(), savedMember3.getUuid().toString()))
                .name("캠핑장에서 누구와 대화")
                .build();
        ChatRoom savedRoom1 = chatRoomRepository.save(chatRoom1);
        ChatRoom savedRoom2 = chatRoomRepository.save(chatRoom2);
        ChatRoom savedRoom3 = chatRoomRepository.save(chatRoom3);

        assertEquals(0, chatService.getMyChatRoomList(savedMember4.getId()).size()); // 채팅방이 없는 유저 반환값
        assertEquals(2, chatService.getMyChatRoomList(savedMember1.getId()).size()); // 채팅방 조회 2개
        List<?> rooms = chatService.getMyChatRoomList(savedMember1.getId());

        for(int i = 0; i < rooms.size(); i++){
            if (rooms.get(i) instanceof MyChatRoomResDTO){ // 조회된 채팅방 상대방 프로필 검사
                assertTrue(((MyChatRoomResDTO) rooms.get(i)).getOtherProfilePath().equals("member2.png") ||
                        ((MyChatRoomResDTO) rooms.get(i)).getOtherProfilePath().equals("member3.png"));
            }
        }

        // 채팅방 생성 역순 조회
        if (rooms.get(0) instanceof MyChatRoomResDTO && rooms.get(1) instanceof MyChatRoomResDTO){
            String date1 = ((MyChatRoomResDTO) rooms.get(0)).getCreatedAt();
            String date2 = ((MyChatRoomResDTO) rooms.get(1)).getCreatedAt();
            assertTrue(LocalDate.parse(date1).isAfter(LocalDate.parse(date2)));
        }

        chatRoomRepository.deleteAll(List.of(savedRoom1, savedRoom2, savedRoom3));
    }
}
