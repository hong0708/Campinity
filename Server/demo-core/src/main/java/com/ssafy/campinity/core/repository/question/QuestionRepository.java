package com.ssafy.campinity.core.repository.question;

import com.ssafy.campinity.core.entity.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Optional<Question> findByUuidAndExpiredIsFalse(UUID questionId);

    List<Question> findAllByCampsite_idAndExpiredIsFalseOrderByCreatedAtDesc(int campsiteId);

    List<Question> findAllByCampsite_idAndMember_idAndExpiredIsFalseOrderByCreatedAtDesc(int campsiteId, int memberId);

    void deleteById(int questionId);

    Optional<Question> findById(int questionId);
}
