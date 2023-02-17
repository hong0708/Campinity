package com.ssafy.campinity.core.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FcmReplyDTO {

    @ApiModelProperty(
            value = "fcm data에서 받은 FcmMessageId",
            required = true,
            dataType = "String"
    )
    private String fcmMessageId;

    @ApiModelProperty(
            value = "유저가 현재 사용하고 있는 기기의 fcm 토큰",
            required = true,
            dataType = "String"
    )
    private String fcmToken;

    @Builder
    public FcmReplyDTO(String fcmMessageId, String fcmToken) {
        this.fcmMessageId = fcmMessageId;
        this.fcmToken = fcmToken;
    }
}
