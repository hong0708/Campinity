package com.ssafy.campinity.core.entity.question;

import com.ssafy.campinity.core.entity.answer.Answer;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.repository.answer.AnswerRepository;
import com.ssafy.campinity.core.repository.campsite.CampsiteRepository;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import com.ssafy.campinity.core.repository.question.QuestionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@Transactional
public class QuestionTest {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    CampsiteRepository campsiteRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Test
    @DisplayName("question 및 answer 생성 테스트")
    public void questionCreateAndDeleteTest (){
        Campsite campsite = campsiteRepository.findById(1).orElseThrow(IllegalArgumentException::new);
        Member member = Member.builder().uuid(UUID.randomUUID()).name("test").build();
        Member savedMember = memberRepository.save(member);

        Question question = Question.builder().uuid(UUID.randomUUID()).content("testtest").member(member).campsite(campsite).build();

        Question savedQuestion = questionRepository.save(question);

        Assertions.assertThat(savedQuestion.getExpired()).isEqualTo(false);

        Answer answer1 = Answer.builder().member(member).content("답글").uuid(UUID.randomUUID()).build();

        answerRepository.save(answer1);

        List<Answer> answers = answerRepository.findAllByQuestion_idAndExpiredIsFalse(savedQuestion.getId());

        Assertions.assertThat(answers.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("exception 발생 테스트")
    public void questionExceptionTest (){
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            questionRepository.findByUuidAndExpiredIsFalse(UUID.randomUUID()).orElseThrow(IllegalArgumentException::new);
        });
    }
}
