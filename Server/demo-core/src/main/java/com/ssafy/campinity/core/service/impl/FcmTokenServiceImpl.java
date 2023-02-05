package com.ssafy.campinity.core.service.impl;


import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.TopicManagementResponse;
import com.ssafy.campinity.core.dto.FcmTokenResDTO;
import com.ssafy.campinity.core.entity.fcm.FcmToken;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.repository.fcm.FcmTokenRepository;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import com.ssafy.campinity.core.service.FcmTokenService;
import com.ssafy.campinity.core.utils.ErrorMessageEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FcmTokenServiceImpl implements FcmTokenService {

    private final MemberRepository memberRepository;
    private final FcmTokenRepository fcmTokenRepository;

    /**
     * @param token
     * @param memberId
     * 유저의 fcm token을 갱신
     *  - 클라이언트단에서 유저의 fcm 토큰을 갱신할 경우 timeStamp 갱신
     *  - 서버단에서 자체 토큰 삭제로 인해 유저가 토큰이 없을 경우 재생성
     * @return
     */
    @Transactional
    public FcmTokenResDTO saveFcmToken(int memberId, String token){
        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessageEnum.USER_NOT_EXIST.getMessage()));

        FcmToken fcmToken = fcmTokenRepository.findByToken(token).orElse(null);
        FcmTokenResDTO fcmTokenResDTO;

        if (fcmToken != null){ // token이 기존에 존재할 경우
            if (fcmToken.getMember().getId() != memberId){ // 요청 유저와 다를 경우 삭제
                Member otherMember = memberRepository.findMemberByIdAndExpiredIsFalse(fcmToken.getMember().getId()).get();
                otherMember.removeFcmToken(fcmToken);
                fcmTokenRepository.deleteById(fcmToken.getId());

                FcmToken newFcmToken = FcmToken.builder()
                        .token(token)
                        .member(member)
                        .expiredDate(LocalDate.now().plusMonths(1))
                        .build();
                member.addFcmToken(newFcmToken);
                fcmTokenResDTO = new FcmTokenResDTO(fcmTokenRepository.save(newFcmToken));
            }
            else {  // 요청 유저와 같을 경우 정보만 갱신
                fcmToken.refreshFcmToken(token);
                fcmTokenResDTO = new FcmTokenResDTO(fcmTokenRepository.save(fcmToken));
            }
        }
        else { // 토큰이 존재하지 않을 경우 신규 생성
            FcmToken newFcmToken = FcmToken.builder()
                    .token(token)
                    .member(member)
                    .expiredDate(LocalDate.now().plusMonths(1))
                    .build();
            member.addFcmToken(newFcmToken);
            fcmTokenResDTO = new FcmTokenResDTO(fcmTokenRepository.save(newFcmToken));
        }
        return fcmTokenResDTO;
    }

    /**
     * @param memberId
     * 유저 로그아웃 시 요청한 유저의 특정 기기 fcm 토큰 삭제
     * @throws FirebaseMessagingException
     */
    @Transactional
    public void deleteFcmToken(int memberId, String token) {
        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessageEnum.USER_NOT_EXIST.getMessage()));
        FcmToken fcmToken = fcmTokenRepository.findByToken(token)
                .orElse(null);

        if (fcmToken != null) {
            member.removeFcmToken(fcmToken);
            fcmTokenRepository.deleteByToken(fcmToken.getToken());
        }
    }

    @Transactional
    public Member subscribeCamp(String campsiteUuid, int memberId){
        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId)
                .orElseThrow(IllegalArgumentException::new);
        member.getFcmTokenList().get(0).subscribeCamp(campsiteUuid);

        Member memberJoinInCamp = memberRepository.save(member);
        return  memberJoinInCamp;
    }

    @Transactional
    public void subscribeTopic(String campsiteUuid, int memberId) throws FirebaseMessagingException {
        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId)
                        .orElseThrow(IllegalArgumentException::new);

        if (member.getFcmTokenList().get(0).getToken().isEmpty())
            throw new NullPointerException("fcm 토큰이 없습니다.");

        if (!member.getFcmTokenList().get(0).getCampsiteUuid().isEmpty()){
            TopicManagementResponse topicManagementResponse = FirebaseMessaging.getInstance()
                    .unsubscribeFromTopic(List.of(member.getFcmTokenList().get(0).getToken()), member.getFcmTokenList().get(0).getCampsiteUuid());
            if(topicManagementResponse.getFailureCount() != 0){
                throw new RuntimeException("알림 취소에 실패했습니다.");
            }
        }

        member.getFcmTokenList().get(0).subscribeCamp(campsiteUuid);
        memberRepository.save(member);

        FirebaseMessaging.getInstance().subscribeToTopic(List.of(member.getFcmTokenList().get(0).getToken()), campsiteUuid);
    }

    @Transactional
    public void unsubscribeTopic(int memberId) throws FirebaseMessagingException {
        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId)
                .orElseThrow(IllegalArgumentException::new);

        TopicManagementResponse topicManagementResponse = FirebaseMessaging.getInstance()
                .unsubscribeFromTopic(List.of(member.getFcmTokenList().get(0).getToken()), member.getFcmTokenList().get(0).getCampsiteUuid());
        if(topicManagementResponse.getFailureCount() != 0){
            throw new RuntimeException("알림 취소에 실패했습니다.");
        }

        if (!member.getFcmTokenList().get(0).getCampsiteUuid().isEmpty()) {
            member.getFcmTokenList().get(0).unsubscribeCamp();
            memberRepository.save(member);
        }
    }
}
