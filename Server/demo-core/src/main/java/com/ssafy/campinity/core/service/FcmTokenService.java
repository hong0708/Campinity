package com.ssafy.campinity.core.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.ssafy.campinity.core.dto.FcmTokenResDTO;
import com.ssafy.campinity.core.entity.fcm.FcmToken;
import com.ssafy.campinity.core.entity.member.Member;
import org.springframework.stereotype.Service;

@Service
public interface FcmTokenService {

    public FcmTokenResDTO saveFcmToken(int memberId, String token);
    public void deleteFcmToken(int memberId, String token);
    public Member subscribeCamp(String campsiteUuid, int memberId) throws FirebaseMessagingException;
    public void subscribeTopic(String campsiteUuid, int memberId) throws FirebaseMessagingException;
    public void unsubscribeTopic(int memberId) throws FirebaseMessagingException;

}
