package com.ssafy.campinity.core.dto;


import com.ssafy.campinity.core.entity.chat.ChatMessage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ChatMessageItemDTO {
    private String context;
    private LocalDateTime timeStamp;
    private String sender;

    @Builder
    public ChatMessageItemDTO(ChatMessage chatMessage, String otherNickname) {
        this.context = chatMessage.getMessage();
        this.timeStamp = chatMessage.getTimestamp();
        this.sender = otherNickname;
    }
}
