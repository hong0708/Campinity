package com.ssafy.campinity.api.dto.res;

import lombok.Getter;

@Getter
public class TokenResponse {
    private final String accessToken;
    private final String tokenType;
    private final String refreshToken;

    public TokenResponse(String accessToken, String tokenType, String refreshToken) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.refreshToken = refreshToken;
    }
}
