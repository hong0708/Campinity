package com.ssafy.campinity.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.campinity.core.dto.SocialUserInfoDto;

import javax.servlet.http.HttpServletResponse;

public interface KakaoUserService {
    SocialUserInfoDto kakaoLogin(String code, HttpServletResponse response) throws JsonProcessingException;
}
