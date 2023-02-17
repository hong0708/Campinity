package com.ssafy.campinity.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.campinity.api.dto.res.TokenResponse;

public interface KakaoUserService {
    TokenResponse kakaoLogin(String accessToken) throws JsonProcessingException;
}
