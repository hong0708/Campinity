package com.ssafy.campinity.api.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.campinity.api.config.security.jwt.JwtProvider;
import com.ssafy.campinity.api.dto.res.TokenResponse;
import com.ssafy.campinity.api.service.KakaoUserService;
import com.ssafy.campinity.core.dto.SocialUserInfoDto;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KakaoUserServiceImpl implements KakaoUserService {
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    public TokenResponse kakaoLogin(String accessToken) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청 -> 안드로이드 앱에서 엑세스토큰을 전송해주기때문에 필요x
        //String accessToken = getAccessToken(code);

        // 2. 토큰으로 카카오 API 호출
        SocialUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);

        // 3. 카카오ID로 회원가입 처리
        Map<String, Object> resMap = registerKakaoUserIfNeed(kakaoUserInfo);

        // 4. 강제 로그인 처리
        Authentication authentication = forceLogin((Member)resMap.get("member"));

        // 5. response Header에 JWT 토큰 추가
        TokenResponse tokenResponse = kakaoUsersAuthorizationInput(authentication, (Boolean)resMap.get("isExist"));
        return tokenResponse;
    }

    // 1. "인가 코드"로 "액세스 토큰" 요청
    private String getAccessToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "7b67ff80038831c2283545ae61432e6c");
        body.add("redirect_uri", "http://localhost:8003/api/v4/members/login-kakao");
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    // 2. 토큰으로 카카오 API 호출
    private SocialUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        // responseBody에 있는 정보를 꺼냄
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        String email = jsonNode.get("kakao_account").get("email").asText();

        return new SocialUserInfoDto(email);
    }

    // 3. 카카오ID로 회원가입 처리
    private Map<String, Object> registerKakaoUserIfNeed (SocialUserInfoDto kakaoUserInfo) {
        // DB 에 중복된 email이 있는지 확인
        Map<String, Object> resMap = new HashMap<>();
        Boolean isExist = true;

        String kakaoEmail = kakaoUserInfo.getEmail();
        Member member = memberRepository.findMemberByEmail(kakaoEmail)
                .orElse(null);

        if (member == null) {
            isExist = false;
            member = Member.builder()
                    .email(kakaoEmail)
                    .uuid(UUID.randomUUID()).build();
            memberRepository.save(member);
        }

        resMap.put("member", member);
        resMap.put("isExist", isExist);

        return resMap;
    }

    // 4. 강제 로그인 처리
    private Authentication forceLogin(Member kakaoUser) {
        UserDetails userDetails = new UserDetailsImpl(kakaoUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    // 5. response Header에 JWT 토큰 추가
    private TokenResponse kakaoUsersAuthorizationInput(Authentication authentication, Boolean isExist) throws JsonProcessingException {
        // response header에 token 추가
        UserDetailsImpl userDetailsImpl = ((UserDetailsImpl) authentication.getPrincipal());
        TokenResponse tokenResponse = jwtProvider.createTokensByLogin(memberRepository.findMemberByEmail(userDetailsImpl.email).get(), isExist);
        return tokenResponse;
    }
}