package com.ssafy.campinity.api.controller;

import com.ssafy.campinity.api.config.security.jwt.MemberDetails;
import com.ssafy.campinity.core.dto.QuestionDetailResDTO;
import com.ssafy.campinity.core.dto.QuestionReqDTO;
import com.ssafy.campinity.core.dto.QuestionResDTO;
import com.ssafy.campinity.core.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v6/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("")
    public ResponseEntity<Object> createQuestion(QuestionReqDTO questionReqDTO, @AuthenticationPrincipal MemberDetails memberDetails) {
        try {
            QuestionResDTO questionResDTO = questionService.createQuestion(questionReqDTO, memberDetails.getMember().getId());
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/api/v1/questions/" + questionResDTO.getQuestionId());
            return new ResponseEntity<>(questionResDTO, headers, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/lists/{campsiteId}")
    public ResponseEntity<List<QuestionResDTO>> getQuestionListByCampsite(@PathVariable UUID campsiteId) {
        try {
            List<QuestionResDTO> result = questionService.getQuestionListByCampsite(campsiteId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/lists/{campsiteId}/members")
    public ResponseEntity<List<QuestionResDTO>> getQuestionListByCampsiteAndUser(@PathVariable UUID campsiteId, @AuthenticationPrincipal MemberDetails memberDetails) {
        try {
            List<QuestionResDTO> result = questionService.getQuestionListByCampsiteAndUser(campsiteId, memberDetails.getMember().getUuid());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionDetailResDTO> getQuestionDetail(@PathVariable UUID questionId) {
        try {
            QuestionDetailResDTO result = questionService.getQuestionDetail(questionId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<Object> deleteQuestion(@PathVariable UUID questionId, @AuthenticationPrincipal MemberDetails memberDetails) {
        try {
            questionService.deleteQuestion(questionId, memberDetails.getMember().getUuid());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
