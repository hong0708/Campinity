package com.ssafy.campinity.core.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatMessageItemDTO {
    private String context;
    private String timeStamp;
    private String senderId;

    @Builder
    public ChatMessageItemDTO(String context, String timeStamp, String senderId) {
        this.context = context;
        this.timeStamp = timeStamp;
        this.senderId = senderId;
    }
}
