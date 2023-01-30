package com.ssafy.campinity.core.repository.fcmToken;

import com.ssafy.campinity.core.entity.fcmToken.FcmToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Integer> {
    void deleteFcmTokenByMember_Id(int memberId);
    Optional<FcmToken> findByMember_Id(int memberId);
}
