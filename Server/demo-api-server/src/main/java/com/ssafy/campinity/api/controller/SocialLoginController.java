package com.ssafy.campinity.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.campinity.core.dto.SocialUserInfoDto;
import com.ssafy.campinity.core.service.KakaoUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class SocialLoginController {
    @Autowired
    private final KakaoUserService kakaoUserService;

    // 카카오 로그인
    @GetMapping("/user/kakao/callback")
    public SocialUserInfoDto kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        System.out.println(code);
        return kakaoUserService.kakaoLogin(code, response);
    }
}