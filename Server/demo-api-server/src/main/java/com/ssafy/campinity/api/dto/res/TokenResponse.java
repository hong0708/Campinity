package com.ssafy.campinity.api.dto.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenResponse {


    @ApiModelProperty(example = "access_token")
    private final String accessToken;

    @ApiModelProperty(example = "Bearer")
    private final String tokenType;

    @ApiModelProperty(example = "refresh_token")
    private final String refreshToken;

    @ApiModelProperty(example = "false")
    private final Boolean isExist;

    @Builder
    public TokenResponse(String accessToken, String tokenType, String refreshToken, Boolean isExist) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.refreshToken = refreshToken;
        this.isExist = isExist;
    }
}
