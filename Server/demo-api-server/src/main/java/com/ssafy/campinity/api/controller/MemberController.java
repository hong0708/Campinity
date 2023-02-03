package com.ssafy.campinity.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.campinity.api.config.security.jwt.JwtProvider;
import com.ssafy.campinity.api.config.security.jwt.MemberDetails;
import com.ssafy.campinity.api.dto.res.TokenResponse;
import com.ssafy.campinity.api.service.KakaoUserService;
import com.ssafy.campinity.core.dto.MemberResDTO;
import com.ssafy.campinity.core.entity.fcmToken.FcmToken;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.service.MemberService;
import com.ssafy.campinity.core.utils.ImageUtil;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;
@Api(tags = "유저 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v4/members")
@Slf4j
public class MemberController {

    private final KakaoUserService kakaoUserService;
    private final JwtProvider jwtProvider;
    private final MemberService memberService;
    private final ImageUtil imageUtil;
    /**
     *
     * @param code
     * @return 멤버의 기본정보(email, nickname, profileImg, UUID)와 함께 refresh token, access token 둘다 제공해야함.
     * @throws JsonProcessingException
     */
    @GetMapping("/login-kakao")
    public ResponseEntity<TokenResponse> kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        TokenResponse tokenResponse = kakaoUserService.kakaoLogin(code);
        return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
    }

    @Transactional
    @PostMapping(value = "/sign-up", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<MemberResDTO> signUp(@RequestPart String nickName,
                                               @RequestPart(required = false) MultipartFile profileImg,
                                               @RequestPart String fcmToken,
                                               @AuthenticationPrincipal MemberDetails memberDetails) throws IOException, NoSuchElementException {
        Member member = memberService.findMemberByUUID(memberDetails.getMember().getUuid());

        String profileImgPath = imageUtil.uploadImage(profileImg, "member");

        member.setProfileImage(profileImgPath);
        member.setName(nickName);
        member.setFcmToken(FcmToken.builder().fcmToken(fcmToken).member(member).build());
        memberService.save(member);

        return new ResponseEntity<>(MemberResDTO.builder()
                        .nickName(member.getName())
                        .profileImg(member.getProfileImage())
                        .email(member.getEmail()).build(), HttpStatus.OK);
    }

    @GetMapping("/nicknames/{nickname}/exists")
    public ResponseEntity<Boolean> checkNickname(@PathVariable String nickname) {
        return new ResponseEntity<>(memberService.checkNicknameDuplicate(nickname), HttpStatus.OK);
    }

    @GetMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) throws JsonProcessingException {
        Member member = memberDetails.getMember();
        return new ResponseEntity<>(jwtProvider.reissueAtk(member), HttpStatus.OK);
    }
}