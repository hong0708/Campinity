package com.ssafy.campinity.core.repository;


import com.ssafy.campinity.core.entity.chat.ChatMessage;
import com.ssafy.campinity.core.entity.chat.ChatRoom;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.repository.chatting.ChatMessageRepository;
import com.ssafy.campinity.core.repository.chatting.ChatRoomRepository;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import com.ssafy.campinity.core.service.ChatService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
public class ChatMessageRepoTest {

    @Autowired
    ChatService chatService;

    @Autowired
    ChatRoomRepository chatRoomRepository;

    @Autowired
    ChatMessageRepository chatMessageRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("채팅 메세지 조회 테스트")
    void ChatMessageRepoTest(){

        Member member1 = Member.builder()
                .email("chat1@chat1.com").name("chat1").profileImage("member1.png").uuid(UUID.randomUUID())
                .build();
        Member savedMember1 = memberRepository.save(member1);
        Member member2 = Member.builder()
                .email("chat2@chat2.com").name("chat2").profileImage("member2.png").uuid(UUID.randomUUID())
                .build();
        Member savedMember2 = memberRepository.save(member2);

        String uuid = UUID.randomUUID().toString();
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(3);
        ChatRoom chatRoom = ChatRoom.builder()
                .id(uuid)
                .fcmMessageBody("body1")
                .createdAt(localDateTime)
                .users(List.of(savedMember1.getUuid().toString(), savedMember2.getUuid().toString()))
                .name("캠핑장에서 누구와 대화")
                .build();
        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);

        ChatMessage chatMessage1 = ChatMessage.builder().message("savedMember1").sender(savedMember1.getUuid().toString()).roomId(savedChatRoom.getId()).build();
        ChatMessage savedChatMessage1 = chatMessageRepository.save(chatMessage1);
        ChatMessage chatMessage2 = ChatMessage.builder().message("savedMember2").sender(savedMember2.getUuid().toString()).roomId(savedChatRoom.getId()).build();
        ChatMessage savedChatMessage2 = chatMessageRepository.save(chatMessage2);

        List<ChatMessage> chatMessageList = chatMessageRepository.findByRoomId(savedChatRoom.getId());
        assertEquals(2, chatMessageList.size());
        ChatMessage test1 = chatMessageList.stream().filter(m -> m.getMessage().equals("savedMember1")).collect(Collectors.toList()).get(0);
        ChatMessage test2 = chatMessageList.stream().filter(m -> m.getMessage().equals("savedMember2")).collect(Collectors.toList()).get(0);

        assertEquals(savedMember1.getUuid().toString(), test1.getSender());
        assertEquals(savedMember2.getUuid().toString(), test2.getSender());

        memberRepository.deleteAll(List.of(savedMember1, savedMember2));
        chatRoomRepository.deleteAll(List.of(savedChatRoom));
        chatMessageRepository.deleteAll(List.of(savedChatMessage1, savedChatMessage2));

    }
}
