package com.ssafy.campinity.core.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;
@Getter
@NoArgsConstructor
public class ChatMessageListDTO {

    private String otherNickname;
    private String otherProfilePath;
    private List<ChatMessageItemDTO> chatMessages;

    @Builder
    public ChatMessageListDTO(String otherNickname, String otherProfilePath, List<ChatMessageItemDTO> chatMessages) {
        this.otherNickname = otherNickname;
        this.otherProfilePath = otherProfilePath;
        this.chatMessages = chatMessages;
    }
}
