package com.ssafy.campinity.api.dto.res;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenResponse {
    private final String accessToken;
    private final String tokenType;
    private final String refreshToken;
    private final Boolean isExist;

    @Builder
    public TokenResponse(String accessToken, String tokenType, String refreshToken, Boolean isExist) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.refreshToken = refreshToken;
        this.isExist = isExist;
    }
}
