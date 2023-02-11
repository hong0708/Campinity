package com.ssafy.campinity.api.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubscribeCamReqDTO {

    @ApiModelProperty(
            value = "기기별 fcm 토큰",
            required = true,
            dataType = "String"
    )
    private String fcmToken;

    @ApiModelProperty(
            value = "알람 신청할 캠핑장 식별 id/ 알람 취소 시 빈 문자열",
            required = true,
            dataType = "String"
    )
    private String campsiteId;

    @Builder
    public SubscribeCamReqDTO(String fcmToken, String campsiteId) {
        this.fcmToken = fcmToken;
        this.campsiteId = campsiteId;
    }
}
