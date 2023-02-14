package com.ssafy.campinity.core.service.impl;

import com.ssafy.campinity.core.dto.MyChatRoomResDTO;
import com.ssafy.campinity.core.entity.chat.ChatRoom;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.repository.chatting.ChatMessageRepository;
import com.ssafy.campinity.core.repository.chatting.ChatRoomRepository;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import com.ssafy.campinity.core.service.ChatService;
import com.ssafy.campinity.core.utils.ErrorMessageEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
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
}
