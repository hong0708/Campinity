package com.ssafy.campinity.api.controller;

import com.ssafy.campinity.api.config.security.jwt.MemberDetails;
import com.ssafy.campinity.core.dto.AnswerReqDTO;
import com.ssafy.campinity.core.dto.AnswerResDTO;
import com.ssafy.campinity.core.service.AnswerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Api(tags = "답변 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v6/answer")
public class AnswerController {

    private final AnswerService answerService;

    @ApiResponses({
            @ApiResponse(code = 201, message = "답변 생성이 성공했을 때 응답"),
            @ApiResponse(code = 400, message = "입력 데이터 부적합(파라미터 이미지 파일 확장자, 타입 및 입력값 부적절 시 응답"),
            @ApiResponse(code = 401, message = "accessToken 부적합 시 응답"),
    })
    @ApiOperation(value = "답변 생성 및 답변 객체 반환하는 API")
    @PostMapping("")
    public ResponseEntity<AnswerResDTO> createAnswer(AnswerReqDTO answerReqDTO, @AuthenticationPrincipal MemberDetails memberDetails) {
        AnswerResDTO result = answerService.createAnswer(answerReqDTO, memberDetails.getMember().getId());
        return new ResponseEntity<>(result, HttpStatus.CREATED);

    }
}