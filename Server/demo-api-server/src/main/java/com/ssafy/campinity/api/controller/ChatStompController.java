package com.ssafy.campinity.api.controller;

import com.ssafy.campinity.core.entity.chat.ChatMessage;
import com.ssafy.campinity.core.service.ChatService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatStompController {

    private final ChatService chatService;

    public ChatStompController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/chat/{room}")
    @SendTo("/room/{roomId}")
    public ChatMessage sendMessage(@DestinationVariable String roomId, ChatMessage message) {
        message.setRoomId(roomId);
        chatService.saveChatMessage(message);
        return message;
    }
}