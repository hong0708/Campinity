package com.ssafy.campinity.api.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmTokenReqDTO {

    @ApiModelProperty(
            value = "기기별 fcm 토큰",
            required = true,
            dataType = "String"
    )
    private String fcmToken;

    @Builder
    public FcmTokenReqDTO(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
