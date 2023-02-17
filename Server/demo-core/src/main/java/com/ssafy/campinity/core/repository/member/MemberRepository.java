package com.ssafy.campinity.core.repository.member;

import com.ssafy.campinity.core.entity.member.Member;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findMemberByUuidAndExpiredIsFalse(UUID memberId);

    Optional<Member> findMemberByIdAndExpiredIsFalse(int memberId);

    Optional<Member> findMemberByEmail(String email);

    Boolean existsByName(String nickname);

    Optional<Member> findByNameAndIdNot(String nickname, Integer memberId);
}
