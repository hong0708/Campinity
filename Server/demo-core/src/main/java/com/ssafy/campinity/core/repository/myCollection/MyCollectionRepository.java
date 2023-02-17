package com.ssafy.campinity.core.repository.myCollection;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ssafy.campinity.core.entity.MyCollection.MyCollection;
import com.ssafy.campinity.core.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyCollectionRepository extends JpaRepository<MyCollection, Integer> {

    List<MyCollection> findByMemberAndExpiredIsFalse(Member member);

    List<MyCollection> findTop5ByMemberAndExpiredIsFalseOrderByUpdatedAtDesc(Member member);

    List<MyCollection> findAllByMemberAndExpiredIsFalse(Member member);
    Optional<MyCollection> findByUuidAndExpiredIsFalse(UUID myCollectionUuid);

    List<MyCollection> findByMember(Member member);
}
