package com.ssafy.campinity.core.dto;

import com.ssafy.campinity.core.entity.fcm.FcmToken;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FcmTokenResDTO {

    private String token;
    private String subscribeCampId;
    private String expiredDate;

    @Builder
    public FcmTokenResDTO(FcmToken fcmToken) {
        this.token = fcmToken.getToken();
        this.subscribeCampId = fcmToken.getCampsiteUuid();
        this.expiredDate = fcmToken.getExpiredDate().toString();
    }
}
