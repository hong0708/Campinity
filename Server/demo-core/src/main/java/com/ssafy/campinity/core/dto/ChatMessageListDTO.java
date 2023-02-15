package com.ssafy.campinity.core.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;
@Getter
@NoArgsConstructor
public class ChatMessageListDTO {
    private List<?> chatMessages;

    @Builder
    public ChatMessageListDTO(List<?> chatMessages) {
        this.chatMessages = chatMessages;
    }
}
