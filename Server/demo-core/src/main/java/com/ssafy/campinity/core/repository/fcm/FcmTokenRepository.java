package com.ssafy.campinity.core.repository.fcm;

import com.ssafy.campinity.core.entity.fcm.FcmToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import java.util.List;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Integer> {

    void deleteByToken(String token);
    Optional<FcmToken> findByMember_Id(int memberId);
    Optional<FcmToken> findByToken(String token);
    List<FcmToken> findAllByMember_Id(int memberId);
    Optional<FcmToken> findByMember_IdAndToken(int memberId, String token);
    List<FcmToken> findTop500ByCampsiteUuidAndMember_IdIsNot(String campsiteUuid, int memberId);
    List<FcmToken> findAllByTokenIn(List<String> tokens);

}
