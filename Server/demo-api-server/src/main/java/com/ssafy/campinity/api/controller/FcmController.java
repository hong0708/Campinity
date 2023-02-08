package com.ssafy.campinity.api.controller;


import com.ssafy.campinity.api.config.security.jwt.MemberDetails;
import com.ssafy.campinity.api.dto.req.FcmTokenReqDTO;
import com.ssafy.campinity.api.dto.req.SubscribeCamReqDTO;
import com.ssafy.campinity.core.dto.FcmMessageReqDTO;
import com.ssafy.campinity.core.dto.FcmTokenResDTO;
import com.ssafy.campinity.core.service.FcmMessageService;
import com.ssafy.campinity.core.service.FcmTokenService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Api(tags = "FCM 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v9/fcm")
public class FcmController {

    private final FcmMessageService fcmMessageService;
    private final FcmTokenService fcmTokenService;

    @ApiResponses({
            @ApiResponse(code = 201, message = "fcm token 저장 성공 시 응답"),
            @ApiResponse(code = 400, message = "유저가 존재하지 않을 경우 응답"),
    })
    @ApiOperation(value = "fcm 저장 및 갱신 api")
    @PostMapping("/token")
    public ResponseEntity<FcmTokenResDTO> saveFcmToken(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody FcmTokenReqDTO fcmTokenReqDTO) {
        FcmTokenResDTO fcmTokenResDTO = fcmTokenService
                .saveFcmToken(memberDetails.getMember().getId(), fcmTokenReqDTO.getFcmToken());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Location", "/api/v9/fcm/" + fcmTokenResDTO.getToken());
        return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(fcmTokenResDTO);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "알람 신청 성공 시 응답"),
            @ApiResponse(code = 400, message = "유저가 존재하지 않을 경우 응답"),
    })
    @ApiOperation(value = "특정 캠핑장 알람 신청 및 취소 api")
    @PutMapping("/subscribe")
    public ResponseEntity<FcmTokenResDTO> subscribeCamp(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody SubscribeCamReqDTO subscribeCamReqDTO) {
        FcmTokenResDTO fcmTokenResDTO = fcmTokenService.subscribeCamp(
                subscribeCamReqDTO.getCampsiteId(),
                memberDetails.getMember().getId(),
                subscribeCamReqDTO.getFcmToken());
        return ResponseEntity.ok().body(fcmTokenResDTO);
    }

    @ApiOperation(value = "해당 유저의 현재 사용하고 있는 기기의 구독 상태 조회 api")
    @GetMapping("/{token}")
    public ResponseEntity<FcmTokenResDTO> findMyFcmToken(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable String token) {

        FcmTokenResDTO fcmTokenResDTO = fcmTokenService.findMyFcmToken(memberDetails.getMember().getId(), token);
        return ResponseEntity.ok().body(fcmTokenResDTO);
    }

    @ApiOperation(value = "구독한 캠핑장에 메시지 전송 api")
    @PostMapping("/to-many")
    public void sendFcmMessageToMany(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody FcmMessageReqDTO fcmMessageReqDTO) throws IOException {

        fcmMessageService.sendMessageToMany(memberDetails.getMember().getId(), fcmMessageReqDTO);
    }
}
