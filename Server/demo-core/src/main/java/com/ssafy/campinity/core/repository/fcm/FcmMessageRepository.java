package com.ssafy.campinity.core.repository.fcm;

import com.ssafy.campinity.core.entity.fcm.FcmMessage;
import com.ssafy.campinity.core.entity.fcm.FcmToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FcmMessageRepository extends JpaRepository<FcmMessage, Integer> {
    Optional<FcmMessage> findByUuidAndExpiredIsFalse(UUID uuid);

    Optional<FcmMessage> findByUuid(UUID uuid);
}
