package com.ssafy.campinity.api.controller;

import com.ssafy.campinity.api.config.security.jwt.MemberDetails;
import com.ssafy.campinity.core.dto.QuestionDetailResDTO;
import com.ssafy.campinity.core.dto.QuestionReqDTO;
import com.ssafy.campinity.core.dto.QuestionResDTO;
import com.ssafy.campinity.core.service.QuestionService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v6/questions")
@Api(tags = "우체통 질문 관련 api")
public class QuestionController {

    private final QuestionService questionService;

    @ApiResponses({
            @ApiResponse(code = 201, message = "질문 생성이 성공했을 때 응답"),
            @ApiResponse(code = 400, message = "입력 데이터 부적합(타입 및 입력값 부적절) 시 응답"),
            @ApiResponse(code = 401, message = "accessToken 부적합 시 응답"),
    })
    @ApiOperation(value = "질문 생성 및 생성된 질문 객체 반환 API")
    @PostMapping("")
    public ResponseEntity<QuestionResDTO> createQuestion(
            @RequestBody QuestionReqDTO questionReqDTO,
            @AuthenticationPrincipal MemberDetails memberDetails) {

        QuestionResDTO questionResDTO = questionService.createQuestion(questionReqDTO, memberDetails.getMember().getId());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v6/questions/" + questionResDTO.getQuestionId());
        return new ResponseEntity<>(questionResDTO, headers, HttpStatus.CREATED);

    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "질문 목록 조회 성공 시 응답"),
            @ApiResponse(code = 400, message = "입력 데이터 부적합(타입 및 입력값 부적절) 시 응답"),
            @ApiResponse(code = 401, message = "accessToken 부적합 시 응답"),
    })
    @ApiOperation(value = "질문 목록 조회(캠핑장 기준) API")
    @GetMapping("/lists/{campsiteId}")
    public ResponseEntity<List<QuestionResDTO>> getQuestionListByCampsite(
            @ApiParam(value = "캠핑장 식별 아이디", required = true, type = "String")
            @PathVariable UUID campsiteId) {

        List<QuestionResDTO> result = questionService.getQuestionListByCampsite(campsiteId);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "질문 목록 조회 성공 시 응답"),
            @ApiResponse(code = 400, message = "입력 데이터 부적합(타입 및 입력값 부적절) 시 응답"),
            @ApiResponse(code = 401, message = "accessToken 부적합 시 응답"),
    })
    @ApiOperation(value = "질문 목록 조회(캠핑장 및 사용자 기준) API")
    @GetMapping("/lists/{campsiteId}/members")
    public ResponseEntity<List<QuestionResDTO>> getQuestionListByCampsiteAndUser(
            @ApiParam(value = "캠핑장 식별 아이디", required = true, type = "String")
            @PathVariable UUID campsiteId,
            @AuthenticationPrincipal MemberDetails memberDetails) {

        List<QuestionResDTO> result = questionService.getQuestionListByCampsiteAndMember(campsiteId, memberDetails.getMember().getId());
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "질문 상세 조회 성공 시 응답"),
            @ApiResponse(code = 400, message = "입력 데이터 부적합(파라미터 타입 및 입력값 부적절) 시 응답"),
            @ApiResponse(code = 401, message = "accessToken 부적합 시 응답"),
    })
    @ApiOperation(value = "질문 상세 조회 API")
    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionDetailResDTO> getQuestionDetail(
            @ApiParam(value = "질문 식별 아이디", required = true, type = "String")
            @PathVariable UUID questionId) {

        QuestionDetailResDTO result = questionService.getQuestionDetail(questionId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "질문 삭제 성공 시 응답"),
            @ApiResponse(code = 400, message = "삭제권한이 없거나 질문 식별 아이디 값 부적절 시 응답"),
            @ApiResponse(code = 401, message = "accessToken 부적합 시 응답"),
    })
    @ApiOperation(value = "질문 삭제 API")
    @DeleteMapping("/{questionId}")
    public ResponseEntity<Object> deleteQuestion(
            @ApiParam(value = "질문 식별 아이디", required = true, type = "String")
            @PathVariable UUID questionId,
            @AuthenticationPrincipal MemberDetails memberDetails) throws Exception {

        questionService.deleteQuestion(questionId, memberDetails.getMember().getUuid());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
