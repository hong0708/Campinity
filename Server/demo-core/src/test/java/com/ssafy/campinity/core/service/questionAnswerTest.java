package com.ssafy.campinity.core.service;


import com.ssafy.campinity.core.dto.QuestionReqDTO;
import com.ssafy.campinity.core.dto.QuestionResDTO;
import com.ssafy.campinity.core.entity.answer.Answer;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.entity.question.Question;
import com.ssafy.campinity.core.repository.answer.AnswerRepository;
import com.ssafy.campinity.core.repository.campsite.CampsiteRepository;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import com.ssafy.campinity.core.repository.question.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class questionAnswerTest {

    @Autowired
    CampsiteRepository campsiteRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    QuestionService questionService;
    @Autowired
    AnswerService answerService;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    AnswerRepository answerRepository;

    /**
     * answer_question_merge_cascade_test
     * 1. question 데이터 db에 영속화
     * 2. answer 데이터 transient 상태에서 persisted question data 주입
     * 3. 피참조 데이터 question에 데이터 변경 변경 사항 입력
     * 4. answer 데이터 db에 영속화 후 조회
     * 5. question이 merge cascade되었는지 확인
     * 6. answer와 question의 answer id 비교
     */
    @Test
    @DisplayName("question&Answer 양방향 관계 merge 영속성 전이 테스트")
    void answerQeustionMergeCascadeTest(){

        Question question = createQuestion();
        Question savedQuestion = questionRepository
                .findById(question.getId())
                .orElseThrow(IllegalArgumentException::new);

        Answer savedAnswer = createAnswer(savedQuestion);

        Question mergedQuestion = questionRepository.findById(savedAnswer.getQuestion().getId()).orElse(null);

        assertEquals(1, mergedQuestion.getAnswers().size());
        assertEquals(mergedQuestion.getAnswers().get(0).getId(), savedAnswer.getId());
    }

    /**
     * 질문 삭제 시 답변으로 삭제 영속성 전이 테스트
     * 1. question 데이터 셍성
     * 2. answer - question 데이터 참조 후 저장
     * 3. question 삭제 후 answer 객체 삭제 여부 확인
     */
    @Test
    @DisplayName("질문 삭제 영송석 전이 테스트")
    void qeustionDeleteCascadeTest(){

        Question question = createQuestion();
        Question savedQuestion = questionRepository
                .findById(question.getId())
                .orElseThrow(IllegalArgumentException::new);

        Answer savedAnswer = createAnswer(savedQuestion);

        int answerId = savedAnswer.getId();

        questionRepository.deleteById(savedAnswer.getQuestion().getId());

        Answer deletedAnswer = answerRepository.findById(answerId).orElse(null);

        assertNull(deletedAnswer,"질문 삭제 영속성 전이 실패");
    }

    // 빈 파일 생성 메서드
    public MultipartFile implementFile(){
        return new MultipartFile() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getOriginalFilename() {
                return null;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {

            }
        };
    }


    // 테스트 통과한 code 기반 질문 객체 생성 method
    public Question createQuestion() {

        Campsite campsite = Campsite.builder()
                .address("전남 담양군 봉산면 탄금길 9-26")
                .allowAnimal("불가능")
                .campName("힐포인트")
                .contentId(125423)
                .dayOperation("평일, 주말")
                .doName("충청남도")
                .latitude(36.8822361)
                .longitude(130.8338106)
                .sigunguName("구미시")
                .uuid(UUID.randomUUID())
                .build();

        Campsite savedCampsite = campsiteRepository.save(campsite);

        Member member = Member.builder()
                .email("test@Tset.com").name("test").profileImage("")
                .build();

        Member savedMember = memberRepository.save(member);

        QuestionReqDTO request = QuestionReqDTO.builder()
                .campsiteId(campsite.getUuid())
                .content("quest test")
                .build();
        QuestionResDTO questionResDTO = questionService.createQuestion(request, savedMember.getId());

        Question savedQuestion = questionRepository
                .findByUuidAndExpiredIsFalse(UUID.fromString(questionResDTO.getQuestionId()))
                .orElseThrow(IllegalArgumentException::new);
        return savedQuestion;
    }

    // 테스트 통과한 code 답변 객체 생성
    public Answer createAnswer(Question question){

        Question savedQuestion = questionRepository
                .findById(question.getId())
                .orElseThrow(IllegalArgumentException::new);

        Member member = Member.builder()
                .email("test1@test1.com").name("test1").profileImage("")
                .build();
        Member savedMember = memberRepository.save(member);

        Answer answer = Answer.builder()
                .question(savedQuestion)
                .uuid(UUID.randomUUID())
                .content("answer test")
                .member(savedMember)
                .build();

        savedQuestion.addAnswer(answer);
        return answerRepository.save(answer);
    }
}
