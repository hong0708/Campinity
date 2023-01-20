package com.ssafy.campinity.core.repository.message;

import com.ssafy.campinity.core.entity.message.LikeMessage;
import com.ssafy.campinity.core.entity.message.Message;
import com.ssafy.campinity.core.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface LikeMessageRepository extends JpaRepository<LikeMessage, Integer> {

    @Transactional
    void deleteByMemberAndMessage(Member member, Message message);

    Optional<LikeMessage> findByMemberAndMessage(Member member, Message message);
}
