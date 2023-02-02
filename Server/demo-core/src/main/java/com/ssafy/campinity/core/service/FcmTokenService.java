package com.ssafy.campinity.core.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.stereotype.Service;

@Service
public interface FcmTokenService {

    public void saveFcmToken(String fcmToken, int memberId);
    public void deleteFcmToken(int memberId) throws FirebaseMessagingException;
    public void refreshFcmToken(int memberId, String fcmToken) throws FirebaseMessagingException;
    public void subscribeTopic(String campsiteUuid, int memberId) throws FirebaseMessagingException;
    public void unsubscribeTopic(int memberId) throws FirebaseMessagingException;

}
