package com.ssafy.campinity.core.service.impl;

import com.ssafy.campinity.core.dto.ChatMessageItemDTO;
import com.ssafy.campinity.core.dto.ChatMessageListDTO;
import com.ssafy.campinity.core.dto.MyChatRoomResDTO;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.chat.ChatMessage;
import com.ssafy.campinity.core.entity.chat.ChatRoom;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.repository.campsite.CampsiteRepository;
import com.ssafy.campinity.core.repository.chatting.ChatMessageRepository;
import com.ssafy.campinity.core.repository.chatting.ChatRoomRepository;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import com.ssafy.campinity.core.service.ChatService;
import com.ssafy.campinity.core.utils.ErrorMessageEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final CampsiteRepository campsiteRepository;
    private final MemberRepository memberRepository;

    public List<?> getMyChatRoomList(int memberId){
        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId).orElseThrow(() ->
                new NoSuchElementException(ErrorMessageEnum.USER_NOT_EXIST.getMessage()));

        List<ChatRoom> chatroomList = chatRoomRepository.findAllByUsersContainingOrderByCreatedAtDesc(member.getUuid().toString());

        if (chatroomList.size() == 0){ return new ArrayList<>(); }

        List<MyChatRoomResDTO> myChatRoomList = new ArrayList<>();
        for(ChatRoom room : chatroomList){
            String otherUuid = room.getUsers().stream().filter(userUuid ->
                    !member.getUuid().toString().equals(userUuid)).collect(Collectors.toList()).get(0);
            Member other = memberRepository.findMemberByUuidAndExpiredIsFalse(UUID.fromString(otherUuid)).orElse(null);

            if (other != null)
                myChatRoomList.add(
                        MyChatRoomResDTO.builder()
                                .room(room)
                                .otherProfilePath(other.getProfileImage())
                                .build());
        }
        return myChatRoomList;
    }

    public void createChatRoom(String campsiteUuid, String appointeeId, String senderId, String fcmMessageBody){

        Campsite campsite = campsiteRepository.findByUuid(UUID.fromString(campsiteUuid)).orElse(null);
        String name;
        if (campsite != null) name = campsite.getCampName();
        else name = "방문했던 캠핑장";

        ChatRoom chatRoom = ChatRoom.builder()
                .name(name)
                .id(UUID.randomUUID().toString())
                .users(List.of(appointeeId, senderId))
                .fcmMessageBody(fcmMessageBody)
                .createdAt(LocalDate.now()).build();
        chatRoomRepository.save(chatRoom);
    }

    @Override
    public ChatMessage saveChatMessage(ChatMessage chatMessage) {
        return chatMessageRepository.save(chatMessage);
    }

    public ChatMessageListDTO getChatMessages(int memberId, String roomId){

        ChatRoom room = chatRoomRepository.findById(roomId).orElseThrow(() ->
                new NoSuchElementException(ErrorMessageEnum.CHATROOM_NOT_EXIST.getMessage()));
        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId).orElseThrow(() ->
                new NoSuchElementException(ErrorMessageEnum.USER_NOT_EXIST.getMessage()));

        List<String> otherUuid = room.getUsers().stream()
                .filter(user -> !user.equals(member.getUuid().toString())).collect(Collectors.toList());
        Member other = memberRepository.findMemberByUuidAndExpiredIsFalse(UUID.fromString(otherUuid.get(0))).orElseThrow(() ->
                new NoSuchElementException(ErrorMessageEnum.USER_NOT_EXIST.getMessage()));

        List<ChatMessage> chatMessageList = chatMessageRepository.findByRoomId(roomId);
        if (chatMessageList.size() == 0) { // 채팅 내용이 없는 경우
            return ChatMessageListDTO.builder()
                    .chatMessages(
                            List.of(
                                    ChatMessageItemDTO.builder()
                                            .chatMessage(ChatMessage.builder().message("").id("").roomId("").sender("").build()).otherNickname("")
                            .build()))
                    .build();
        }

        List<ChatMessageItemDTO> itemList = new ArrayList<>();
        for (ChatMessage item : chatMessageList) {
            if (item.getSender().equals(other.getUuid().toString()))
                itemList.add(ChatMessageItemDTO.builder().chatMessage(item).otherNickname(other.getName()).build());
            else
                itemList.add(ChatMessageItemDTO.builder().chatMessage(item).otherNickname(member.getName()).build());
        }

        return ChatMessageListDTO.builder().chatMessages(itemList).build();
    }
}
