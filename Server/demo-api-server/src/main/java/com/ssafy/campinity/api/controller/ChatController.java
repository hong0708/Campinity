package com.ssafy.campinity.api.controller;

import com.ssafy.campinity.api.config.security.jwt.MemberDetails;
import com.ssafy.campinity.core.dto.MyChatRoomResDTO;
import com.ssafy.campinity.core.entity.chat.ChatMessage;
import com.ssafy.campinity.core.service.ChatService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v10/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    @ApiOperation(value = "내가 속한 채팅방 목록을 조회하는 API입니다. \n채팅방이 없는 경우 빈 배열로 반환됩니다.")
    @GetMapping
    public ResponseEntity<List<?>> getMyChatList(
            @AuthenticationPrincipal MemberDetails memberDetails){
        List<?> myChatRoomList = chatService.getMyChatRoomList(memberDetails.getMember().getId());
        return ResponseEntity.ok().body(myChatRoomList);
    }

}