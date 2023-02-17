package com.ssafy.campinity.core.service.impl;

import com.ssafy.campinity.core.dto.AnswerResDTO;
import com.ssafy.campinity.core.dto.QuestionDetailResDTO;
import com.ssafy.campinity.core.dto.QuestionReqDTO;
import com.ssafy.campinity.core.dto.QuestionResDTO;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.entity.question.Question;
import com.ssafy.campinity.core.exception.BadRequestException;
import com.ssafy.campinity.core.repository.answer.AnswerRepository;
import com.ssafy.campinity.core.repository.campsite.CampsiteRepository;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import com.ssafy.campinity.core.repository.question.QuestionRepository;
import com.ssafy.campinity.core.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.NoPermissionException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final MemberRepository memberRepository;
    private final CampsiteRepository campsiteRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;


    @Override
    public QuestionResDTO createQuestion(QuestionReqDTO questionReqDTO, int memberId) {
        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId).orElseThrow(IllegalArgumentException::new);
        Campsite campsite = campsiteRepository.findByUuid(questionReqDTO.getCampsiteId()).orElseThrow(IllegalArgumentException::new);

        Question question = Question.builder().member(member).campsite(campsite).
                content(questionReqDTO.getContent()).uuid(UUID.randomUUID()).build();

        Question savedQuestion = questionRepository.save(question);

        return QuestionResDTO.builder().question(savedQuestion).build();
    }

    @Override
    public List<QuestionResDTO> getQuestionListByCampsite(UUID campsiteId) {
        Campsite campsite = campsiteRepository.findByUuid(campsiteId).orElseThrow(IllegalArgumentException::new);

        return questionRepository.findAllByCampsite_idAndExpiredIsFalseOrderByCreatedAtDesc(campsite.getId()).stream().
                map(question -> {return QuestionResDTO.builder().question(question).build();}).
                collect(Collectors.toList());
    }

    @Override
    public List<QuestionResDTO> getQuestionListByCampsiteAndMember(UUID campsiteId, int memberId) {
        Campsite campsite = campsiteRepository.findByUuid(campsiteId).orElseThrow(IllegalArgumentException::new);
        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId).orElseThrow(IllegalArgumentException::new);

        return questionRepository.findAllByCampsite_idAndMember_idAndExpiredIsFalseOrderByCreatedAtDesc(campsite.getId(), member.getId()).
                stream().map(question -> {return QuestionResDTO.builder().question(question).build();})
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public QuestionDetailResDTO getQuestionDetail(UUID questionId) {
        Question question = questionRepository.findByUuidAndExpiredIsFalse(questionId).orElseThrow(IllegalArgumentException::new);

        List<AnswerResDTO> answers = answerRepository.findAllByQuestion_idAndExpiredIsFalse(question.getId()).stream().
                map(answer -> {return AnswerResDTO.builder().answer(answer).build();}).collect(Collectors.toList());

        return QuestionDetailResDTO.builder().question(question).answers(answers).build();
    }

    @Override
    public void deleteQuestion(UUID questionId, UUID memberUuid) {
        Question question = questionRepository.findByUuidAndExpiredIsFalse(questionId).orElseThrow(IllegalArgumentException::new);

        if (question.getMember().getUuid().equals(memberUuid)) {
            answerRepository.deleteByQuestion_id(question.getId());
            questionRepository.deleteById(question.getId());
        }
        else {
            throw new BadRequestException("삭제 권한이 없습니다.");
        }
    }

    @Override
    public List<QuestionResDTO> getQuestionListByMember(int memberId) {
        return questionRepository.findByMember_idAndExpiredIsFalseOrderByCreatedAtDesc(memberId)
                .stream().map(question -> {
                    return QuestionResDTO.builder().question(question).build();
                }).collect(Collectors.toList());
    }
}
