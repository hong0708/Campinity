package com.ssafy.campinity.core.dto;

import com.ssafy.campinity.core.entity.fcm.FcmToken;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FcmTokenResDTO {

    @ApiModelProperty(example = "현재 유저의 사용중인 기기의 fcm 토큰")
    private String token;
    @ApiModelProperty(example = "알람 신청 중인 캠핑장 식별 id")
    private String subscribeCampId;
    @ApiModelProperty(example = "토큰 만료일")
    private String expiredDate;

    @Builder
    public FcmTokenResDTO(FcmToken fcmToken) {
        this.token = fcmToken.getToken();
        this.subscribeCampId = fcmToken.getCampsiteUuid();
        this.expiredDate = fcmToken.getExpiredDate().toString();
    }
}
