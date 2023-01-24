package com.ssafy.campinity.core.repository.member;

import com.ssafy.campinity.core.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findMemberByUuidAndExpiredIsFalse(UUID memberId);

    Optional<Member> findMemberByIdAndExpiredIsFalse(int memberId);

    Optional<Member> findMemberByEmail(String email);
    Boolean existsByName(String nickname);
}
