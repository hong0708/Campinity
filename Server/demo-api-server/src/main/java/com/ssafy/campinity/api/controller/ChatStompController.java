package com.ssafy.campinity.api.controller;

import com.ssafy.campinity.core.entity.chat.ChatMessage;
import com.ssafy.campinity.core.service.ChatService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatStompController {

    private final ChatService chatService;
    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달
    public ChatStompController(ChatService chatService, SimpMessagingTemplate template) {
        this.chatService = chatService;
        this.template = template;
    }

    @MessageMapping("/chat/{roomId}")
    public void sendMessage(@DestinationVariable String roomId, ChatMessage message) {
        message.setRoomId(roomId);
        chatService.saveChatMessage(message);
        template.convertAndSend("/room/" + roomId, message);
    }
}