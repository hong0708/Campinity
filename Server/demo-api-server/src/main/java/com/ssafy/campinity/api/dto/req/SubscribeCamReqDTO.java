package com.ssafy.campinity.api.dto.req;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubscribeCamReqDTO {
    private String fcmToken;
    private String campsiteId;

    @Builder
    public SubscribeCamReqDTO(String fcmToken, String campsiteId) {
        this.fcmToken = fcmToken;
        this.campsiteId = campsiteId;
    }
}
