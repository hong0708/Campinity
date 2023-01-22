package com.ssafy.campinity.core.service;

import com.ssafy.campinity.core.dto.AnswerReqDTO;
import com.ssafy.campinity.core.dto.AnswerResDTO;
import org.springframework.stereotype.Service;

@Service
public interface AnswerService {

    public AnswerResDTO createAnswer(AnswerReqDTO answerReqDTO);

}
