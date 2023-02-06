package com.ssafy.campinity.api.dto.req;


import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LogoutReqDTO {

    @ApiModelProperty(example = "fcm_token")
    private String fcmToken;

    @ApiModelProperty(example = "access_token")
    private String atk;

    @ApiModelProperty(example = "refresh_token")
    private String rtk;

    @Builder
    public LogoutReqDTO(String fcmToken, String atk, String rtk) {
        this.fcmToken = fcmToken;
        this.atk = atk;
        this.rtk = rtk;
    }
}
