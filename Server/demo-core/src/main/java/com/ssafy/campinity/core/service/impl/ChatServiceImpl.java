package com.ssafy.campinity.core.service.impl;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ChatServiceImpl implements ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final CampsiteRepository campsiteRepository;
    private final MemberRepository memberRepository;
    private final ChatMessageRepository chatMessageRepository;

    public ChatServiceImpl(ChatRoomRepository chatRoomRepository, CampsiteRepository campsiteRepository, MemberRepository memberRepository, ChatMessageRepository chatMessageRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.campsiteRepository = campsiteRepository;
        this.memberRepository = memberRepository;
        this.chatMessageRepository = chatMessageRepository;
    }

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

//    public ChatMessageListDTO getChatMessages(int memberId, String roomId){
//        return new ChatMessageListDTO();
//    }
}
