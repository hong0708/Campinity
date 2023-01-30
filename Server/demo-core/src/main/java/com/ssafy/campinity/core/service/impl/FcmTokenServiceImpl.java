package com.ssafy.campinity.core.service.impl;


import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.TopicManagementResponse;
import com.ssafy.campinity.core.entity.fcmToken.FcmToken;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.repository.fcmToken.FcmTokenRepository;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FcmTokenServiceImpl {

    private final MemberRepository memberRepository;
    private final FcmTokenRepository fcmTokenRepository;

    @Transactional
    public void saveFcmToken(String fcmToken, int memberId){
        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId)
                .orElseThrow(IllegalArgumentException::new);

        FcmToken token = FcmToken.builder()
                .fcmToken(fcmToken)
                .build();

        member.setFcmToken(token);
        memberRepository.save(member);
    }

    @Transactional
    public void deleteFcmToken(int memberId) throws FirebaseMessagingException {
        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId)
                .orElseThrow(IllegalArgumentException::new);

        FirebaseMessaging.getInstance()
                .unsubscribeFromTopic(List.of(member.getFcmToken().getFcmToken()), member.getFcmToken().getCampsiteUuid());

        fcmTokenRepository.deleteFcmTokenByMember_Id(memberId);
    }

    @Transactional
    public void refreshFcmToken(int memberId, String fcmToken) throws FirebaseMessagingException {
        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId)
                .orElseThrow(IllegalArgumentException::new);

        FirebaseMessaging.getInstance()
                .unsubscribeFromTopic(List.of(member.getFcmToken().getFcmToken()), member.getFcmToken().getCampsiteUuid());
        FirebaseMessaging.getInstance()
                .subscribeToTopic(List.of(fcmToken), member.getFcmToken().getCampsiteUuid());

        member.getFcmToken().refreshMemberFcmToken(fcmToken);
        memberRepository.save(member);
    }

    @Transactional
    public void subscribeTopic(String campsiteUuid, int memberId) throws FirebaseMessagingException {
        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId)
                        .orElseThrow(IllegalArgumentException::new);

        if (member.getFcmToken().getFcmToken().isEmpty())
            throw new NullPointerException("fcm 토큰이 없습니다.");

        if (!member.getFcmToken().getCampsiteUuid().isEmpty()){
            TopicManagementResponse topicManagementResponse = FirebaseMessaging.getInstance()
                    .unsubscribeFromTopic(List.of(member.getFcmToken().getFcmToken()), member.getFcmToken().getCampsiteUuid());
            if(topicManagementResponse.getFailureCount() != 0){
                throw new RuntimeException("알림 취소에 실패했습니다.");
            }
        }

        member.getFcmToken().subscribeCamp(campsiteUuid);
        memberRepository.save(member);

        FirebaseMessaging.getInstance().subscribeToTopic(List.of(member.getFcmToken().getFcmToken()), campsiteUuid);
    }

    @Transactional
    public void unsubscribeTopic(int memberId) throws FirebaseMessagingException {
        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId)
                .orElseThrow(IllegalArgumentException::new);

        TopicManagementResponse topicManagementResponse = FirebaseMessaging.getInstance()
                .unsubscribeFromTopic(List.of(member.getFcmToken().getFcmToken()), member.getFcmToken().getCampsiteUuid());
        if(topicManagementResponse.getFailureCount() != 0){
            throw new RuntimeException("알림 취소에 실패했습니다.");
        }

        if (!member.getFcmToken().getCampsiteUuid().isEmpty()) {
            member.getFcmToken().unsubscribeCamp();
            memberRepository.save(member);
        }
    }
}
