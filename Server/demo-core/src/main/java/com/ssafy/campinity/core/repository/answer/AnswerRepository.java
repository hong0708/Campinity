package com.ssafy.campinity.core.repository.answer;

import com.ssafy.campinity.core.entity.answer.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    List<Answer> findAllByQuestion_idAndExpiredIsFalse(int questionId);

    @Transactional
    void deleteByQuestion_id(int questionId);

}
