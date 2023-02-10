package com.ssafy.campinity.core.service;

import com.ssafy.campinity.core.dto.FcmTokenResDTO;
import org.springframework.stereotype.Service;

@Service
public interface FcmTokenService {

    FcmTokenResDTO saveFcmToken(int memberId, String token);
    boolean deleteFcmToken(int memberId, String token);
    FcmTokenResDTO subscribeCamp(String campsiteUuid, int memberId, String fcmToken);
    FcmTokenResDTO findMyFcmToken(int memberId, String token);

}
