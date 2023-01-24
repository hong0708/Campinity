package com.ssafy.campinity.api.controller;

import com.ssafy.campinity.api.config.security.jwt.MemberDetails;
import com.ssafy.campinity.core.dto.AnswerReqDTO;
import com.ssafy.campinity.core.dto.AnswerResDTO;
import com.ssafy.campinity.core.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v6/answer")
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping("")
    public ResponseEntity<Object> createAnswer(AnswerReqDTO answerReqDTO, @AuthenticationPrincipal MemberDetails memberDetails) {
        try {
            AnswerResDTO result = answerService.createAnswer(answerReqDTO, memberDetails.getMember().getId());
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
