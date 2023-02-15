package com.ssafy.campinity.core.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;
@Getter
@NoArgsConstructor
public class ChatMessageListDTO {
    private List<ChatMessageItemDTO> chatMessages;

    @Builder
    public ChatMessageListDTO(List<ChatMessageItemDTO> chatMessages) {
        this.chatMessages = chatMessages;
    }
}
