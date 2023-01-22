package com.ssafy.campinity.api.controller;

import com.ssafy.campinity.core.dto.QuestionDetailResDTO;
import com.ssafy.campinity.core.dto.QuestionReqDTO;
import com.ssafy.campinity.core.dto.QuestionResDTO;
import com.ssafy.campinity.core.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @PostMapping("")
    public ResponseEntity<Object> createQuestion(QuestionReqDTO questionReqDTO) {
        try {
            QuestionResDTO questionResDTO = questionService.createQuestion(questionReqDTO);
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

    @GetMapping("/lists/{campsiteId}/members/{memberId}")
    public ResponseEntity<List<QuestionResDTO>> getQuestionListByCampsiteAndUser(@PathVariable UUID campsiteId, @PathVariable UUID memberId) {
        try {
            List<QuestionResDTO> result = questionService.getQuestionListByCampsiteAndUser(campsiteId, memberId);
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
    public ResponseEntity<Object> deleteQuestion(@PathVariable UUID questionId) {
        try {
            questionService.deleteQuestion(questionId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
