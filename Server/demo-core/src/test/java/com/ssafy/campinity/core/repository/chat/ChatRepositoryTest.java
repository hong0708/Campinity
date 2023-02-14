package com.ssafy.campinity.core.repository.chat;


import com.ssafy.campinity.core.entity.chat.ChatMessage;
import com.ssafy.campinity.core.entity.chat.ChatRoom;
import com.ssafy.campinity.core.repository.chatting.ChatMessageRepository;
import com.ssafy.campinity.core.repository.chatting.ChatRoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class ChatRepositoryTest {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Test
    @DisplayName("채팅방 저장 테스트")
    public void chatRoomShouldBeSave() {

        ChatRoom chatRoom = makeChatRoom();
        assertEquals(chatRoom.getUsers().size(), chatRoom.getUsers().size());
        chatRoomRepository.deleteAll(List.of(chatRoom));
    }

//    @Test
//    public void delteAllData() {
//        chatMessageRepository.deleteAll();
//        chatRoomRepository.deleteAll();
//
//        assertEquals(0, chatMessageRepository.findAll().size());
//        assertEquals(0, chatRoomRepository.findAll().size());
//    }
    @Test
    @DisplayName("채팅메세지 저장 테스트")
    public void chatMessageShouldBeSave() {
        String msg1 = "hi";
        String msg2 = "hihi";

        ChatMessage chatMessage1 = ChatMessage.builder().message(msg1).roomId("1").sender("user1").timestamp(new Date()).build();
        ChatMessage chatMessage2 = ChatMessage.builder().message(msg1).roomId("1").sender("user2").timestamp(new Date()).build();

        ChatMessage retChatMessage1 = chatMessageRepository.save(chatMessage1);
        ChatMessage retChatMessage2 =chatMessageRepository.save(chatMessage2);

        assertEquals(2, chatMessageRepository.findAll().size());
        chatMessageRepository.deleteAll(List.of(retChatMessage1, retChatMessage2));
    }

    @Test
    @DisplayName("roomID로 대화내용 가져오기")
    public void getMessagesFromRoom() {

        ChatRoom chatRoom = makeChatRoom();

        ChatRoom searchedRoom = chatRoomRepository.save(chatRoom);
        String roomId = chatRoom.getId();

        ChatMessage chatMessage1 = ChatMessage.builder().message("msg1").roomId(roomId).sender("user1").timestamp(new Date()).build();
        ChatMessage chatMessage2 = ChatMessage.builder().message("msg2").roomId(roomId).sender("user2").timestamp(new Date()).build();

        ChatMessage retChatMessage1 = chatMessageRepository.save(chatMessage1);
        ChatMessage retChatMessage2 =chatMessageRepository.save(chatMessage2);

        List<ChatMessage> chatMessages = chatMessageRepository.findByRoomId(roomId);

        assertEquals(2, chatMessages.size());
        chatMessageRepository.deleteAll(List.of(retChatMessage1, retChatMessage2));
        chatRoomRepository.deleteAll(List.of(searchedRoom));
    }

    private ChatRoom makeChatRoom() {
        String userId = "user1";
        String userId2 = "user2";

        List<String> userIdList = new ArrayList<>();
        userIdList.add(userId);
        userIdList.add(userId2);

        return chatRoomRepository.save(ChatRoom.builder()
                .name("채팅방1")
                .users(userIdList).build());
    }
}
