package com.ssafy.campinity.core.service.impl;

import com.ssafy.campinity.core.dto.QuestionDetailResDTO;
import com.ssafy.campinity.core.dto.QuestionReqDTO;
import com.ssafy.campinity.core.dto.QuestionResDTO;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.entity.question.Question;
import com.ssafy.campinity.core.repository.campsite.CampsiteRepository;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import com.ssafy.campinity.core.repository.question.QuestionRepository;
import com.ssafy.campinity.core.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CampsiteRepository campsiteRepository;

    @Autowired
    QuestionRepository questionRepository;


    @Override
    public QuestionResDTO createQuestion(QuestionReqDTO questionReqDTO) {
        Member member = memberRepository.findMemberByUuidAndExpiredIsFalse(questionReqDTO.getMemberId()).orElseThrow(IllegalArgumentException::new);
        Campsite campsite = campsiteRepository.findByUuid(questionReqDTO.getCampsiteId()).orElseThrow(IllegalArgumentException::new);

        Question question = Question.builder().member(member).campsite(campsite).
                content(questionReqDTO.getContent()).uuid(UUID.randomUUID()).build();

        Question savedQuestion = questionRepository.save(question);

        return QuestionResDTO.builder().question(savedQuestion).build();
    }

    @Override
    public List<QuestionResDTO> getQuestionListByCampsite(UUID campsiteId) {
        Campsite campsite = campsiteRepository.findByUuid(campsiteId).orElseThrow(IllegalArgumentException::new);

        return questionRepository.findAllByCampsite_idAndExpiredIsFalse(campsite.getId()).stream().
                map(question -> {return QuestionResDTO.builder().question(question).build();}).
                collect(Collectors.toList());
    }

    @Override
    public List<QuestionResDTO> getQuestionListByCampsiteAndUser(UUID campsiteId, UUID memberId) {
        Campsite campsite = campsiteRepository.findByUuid(campsiteId).orElseThrow(IllegalArgumentException::new);
        Member member = memberRepository.findMemberByUuidAndExpiredIsFalse(memberId).orElseThrow(IllegalArgumentException::new);

        return questionRepository.findAllByCampsite_idAndMember_idAndExpiredIsFalse(campsite.getId(), member.getId()).
                stream().map(question -> {return QuestionResDTO.builder().question(question).build();})
                .collect(Collectors.toList());
    }


    // QuestionDetailResDTO에 answer 리스트 추가하기
    // expired된 글을 찾으면 에러 발생
    @Override
    public QuestionDetailResDTO getQuestionDetail(UUID questionId) {
        Question question = questionRepository.findByUuidAndExpiredIsFalse(questionId).orElseThrow(IllegalArgumentException::new);
        return QuestionDetailResDTO.builder().question(question).build();
    }

    @Override
    public void deleteQuestion(UUID questionId) {
        Question question = questionRepository.findByUuidAndExpiredIsFalse(questionId).orElseThrow(IllegalArgumentException::new);
        questionRepository.deleteById(question.getId());
    }

}
