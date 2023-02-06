package com.ssafy.campinity.api.dto.req;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SubscribeCamReqDTO {
    private String fcmToken;

    @Builder
    public SubscribeCamReqDTO(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
