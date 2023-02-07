package com.ssafy.campinity.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.campinity.api.config.security.jwt.JwtProvider;
import com.ssafy.campinity.api.config.security.jwt.MemberDetails;
import com.ssafy.campinity.api.dto.req.EditMemberInfoReqDTO;
import com.ssafy.campinity.api.dto.req.LogoutReqDTO;
import com.ssafy.campinity.api.dto.res.TokenResponse;
import com.ssafy.campinity.api.service.KakaoUserService;
import com.ssafy.campinity.core.dto.FcmTokenResDTO;
import com.ssafy.campinity.core.dto.MemberResDTO;
import com.ssafy.campinity.core.dto.ProfileResDTO;
import com.ssafy.campinity.core.entity.fcm.FcmToken;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.repository.redis.RedisDao;
import com.ssafy.campinity.core.service.FcmTokenService;
import com.ssafy.campinity.core.service.MemberService;
import com.ssafy.campinity.core.utils.ImageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

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
    private final RedisDao redisDao;
    private final FcmTokenService fcmTokenService;

    /**
     *
     * @param accessToken
     * @return 멤버의 기본정보(email, nickname, profileImg, UUID)와 함께 refresh token, access token 둘다 제공해야함.
     * @throws JsonProcessingException
     */
    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 로그인 됐을경우"),
            @ApiResponse(code = 400, message = "요청 파라미터 오류")
    })
    @ApiOperation(value = "로그인 API")
    @GetMapping("/login-kakao")
    public ResponseEntity<TokenResponse> kakaoLogin(@RequestParam String accessToken) throws JsonProcessingException {
        TokenResponse tokenResponse = kakaoUserService.kakaoLogin(accessToken);
        return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 회원가입 됐을경우"),
            @ApiResponse(code = 400, message = "요청 파라미터 오류")
    })
    @ApiOperation(value = "회원가입 API")
    @Transactional
    @PostMapping(value = "/sign-up", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<MemberResDTO> signUp(@RequestPart String nickName,
                                               @RequestPart(required = false) MultipartFile profileImg,
                                               @RequestPart String fcmToken,
                                               @AuthenticationPrincipal MemberDetails memberDetails) throws IOException, NoSuchElementException {
        Member member = memberService.findMemberByUUID(memberDetails.getMember().getUuid());
        String profileImgPath = "";

        if (profileImg != null) {
            profileImgPath = imageUtil.uploadImage(profileImg, "member");
        }

        member.setProfileImage(profileImgPath);
        member.setName(nickName);
        member.addFcmToken(FcmToken.builder()
                .token(fcmToken)
                .member(member)
                .expiredDate(LocalDate.now().plusMonths(1)).build());
        memberService.save(member);

        return new ResponseEntity<>(MemberResDTO.builder()
                        .nickName(member.getName())
                        .profileImg(member.getProfileImage())
                        .email(member.getEmail()).build(), HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 수정 됐을경우"),
            @ApiResponse(code = 400, message = "요청 파라미터 오류")
    })
    @ApiOperation(value = "회원정보 수정 API")
    @Transactional
    @PostMapping(value = "/edit-member-info", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MemberResDTO> editMemberInfo(
            @Parameter(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)) EditMemberInfoReqDTO editMemberInfoReqDTO,
            @AuthenticationPrincipal MemberDetails memberDetails) throws IOException, NoSuchElementException {
        Member member = memberService.findMemberByUUID(memberDetails.getMember().getUuid());
        String profileImgPath = member.getProfileImage();
        MultipartFile profileImg = editMemberInfoReqDTO.getProfileImg();
        String nickName = editMemberInfoReqDTO.getNickName();

        // 유저가 프로필 이미지를 바꾼 경우 (profileImg에 파일 존재)
        if (editMemberInfoReqDTO.getIsChanged()) {
            if (!ObjectUtils.isEmpty(member.getProfileImage()) && profileImg != null) {
                // 기존 프로필 이미지 제거
                imageUtil.removeImage(member.getProfileImage());
            }
            profileImgPath = imageUtil.uploadImage(profileImg, "member");
        }

        member.setProfileImage(profileImgPath);
        member.setName(nickName);;
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

    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 발급됐을 경우"),
            @ApiResponse(code = 400, message = "JWT인증 오류")
    })
    @ApiOperation(value = "accessToken 재발급 API")
    @GetMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) throws JsonProcessingException {
        Member member = memberDetails.getMember();
        return new ResponseEntity<>(jwtProvider.reissueAtk(member), HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 로그아웃 됐을 경우"),
            @ApiResponse(code = 400, message = "존재하지 않는 회원일 경우")
    })
    @ApiOperation(value = "로그아웃 API")
    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(@RequestBody LogoutReqDTO logoutDTO, @AuthenticationPrincipal MemberDetails memberDetails) {

        // refresh token 삭제
        if(redisDao.getValues(memberDetails.getMember().getEmail()) != null) {
            redisDao.deleteValues(memberDetails.getMember().getEmail());
        }

        // 유저가 가진 fcm token 삭제
        fcmTokenService.deleteFcmToken(memberDetails.getMember().getId(), logoutDTO.getFcmToken());

        // 해당 Access Token 유효시간 가지고 와서 BlackList 로 저장하기
        Long expiration = jwtProvider.getExpiration(logoutDTO.getAtk());
        redisDao.setValues(logoutDTO.getAtk(), "logout", expiration, TimeUnit.MILLISECONDS);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    /**
     * @param memberDetails
     * 로그인 직후 이기 때문에 반드시 accessToken은 유효하다. 따라서 memberDetails에 유효한 객체가 삽입됨.
     * @return
     */
    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 유저 삭제 됐을때"),
            @ApiResponse(code = 400, message = "존재하지 않는 회원일 경우")
    })
    @ApiOperation(value = "유저 회원가입 이탈시 유저 삭제 API")
    @PostMapping("/cancel-sign-up")
    public ResponseEntity<Boolean> cancelSignUp(@AuthenticationPrincipal MemberDetails memberDetails) {
        Member member = memberService.findMemberById(memberDetails.getMember().getId());
        Boolean res = false;

        if (!ObjectUtils.isEmpty(member) && member.getName() == null) {
            if (memberService.deleteMemberHard(member.getId()) > 0)
                res = true;
        }

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/profiles")
    public ResponseEntity<ProfileResDTO> getMemberInfo(
            @AuthenticationPrincipal MemberDetails memberDetails) {
        ProfileResDTO profileResDTO = memberService.getMemberProfile(memberDetails.getMember().getId());
        return new ResponseEntity<>(profileResDTO, HttpStatus.OK);
    }
}