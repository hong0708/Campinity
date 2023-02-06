package com.ssafy.campinity.core.service;

import com.ssafy.campinity.core.dto.FcmTokenResDTO;
import org.springframework.stereotype.Service;

@Service
public interface FcmTokenService {

    public FcmTokenResDTO saveFcmToken(int memberId, String token);
    public boolean deleteFcmToken(int memberId, String token);
    public FcmTokenResDTO subscribeCamp(String campsiteUuid, int memberId, String fcmToken);

}
