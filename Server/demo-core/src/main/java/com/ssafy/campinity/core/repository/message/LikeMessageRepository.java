package com.ssafy.campinity.core.repository.message;

import com.ssafy.campinity.core.entity.message.LikeMessage;
import com.ssafy.campinity.core.entity.message.Message;
import com.ssafy.campinity.core.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

public interface LikeMessageRepository extends JpaRepository<LikeMessage, Integer> {

//    @Transactional
//    @Query(value = "delete from LikeMessage as lm where lm.member = :member and lm.message = :message")
//    void deleteByMemberAndMessage(@Param("member") Member member, @Param("message") Message message);

    void deleteByMemberAndMessage(Member member, Message message);

    Optional<LikeMessage> findByMemberAndMessage(Member member, Message message);
    List<LikeMessage> findByMember(Member member);


}
