package com.ssafy.campinity.core.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FcmReplyDTO {

    private String fcmMessageId;
    private String fcmToken;

    @Builder
    public FcmReplyDTO(String fcmMessageId, String fcmToken) {
        this.fcmMessageId = fcmMessageId;
        this.fcmToken = fcmToken;
    }
}
