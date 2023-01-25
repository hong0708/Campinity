package com.ssafy.campinity.core.service;

import com.ssafy.campinity.core.dto.QuestionDetailResDTO;
import com.ssafy.campinity.core.dto.QuestionReqDTO;
import com.ssafy.campinity.core.dto.QuestionResDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface QuestionService {
    QuestionResDTO createQuestion(QuestionReqDTO questionReqDTO, int memberId);

    List<QuestionResDTO> getQuestionListByCampsite(UUID campsiteId);

    List<QuestionResDTO> getQuestionListByCampsiteAndMember(UUID campsiteId, int memeberId);

    QuestionDetailResDTO getQuestionDetail(UUID questionId);

    void deleteQuestion(UUID questionId, UUID memberId) throws Exception;
}
