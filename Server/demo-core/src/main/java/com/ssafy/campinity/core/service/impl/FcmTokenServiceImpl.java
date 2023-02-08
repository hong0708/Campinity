package com.ssafy.campinity.core.service.impl;


import com.google.firebase.messaging.FirebaseMessagingException;
import com.ssafy.campinity.core.dto.FcmTokenResDTO;
import com.ssafy.campinity.core.entity.fcm.FcmToken;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.repository.fcm.FcmTokenRepository;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import com.ssafy.campinity.core.service.FcmTokenService;
import com.ssafy.campinity.core.utils.ErrorMessageEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
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
            if (fcmToken.getMember().getId() != memberId){ // 토큰 소유자가 요청 유저와 다를 경우 삭제
                Member otherMember = memberRepository.findMemberByIdAndExpiredIsFalse(fcmToken.getMember().getId()).get();
                fcmTokenRepository.deleteById(fcmToken.getId());
                otherMember.removeFcmToken(fcmToken);

                FcmToken newFcmToken = FcmToken.builder()
                        .token(token)
                        .member(member)
                        .expiredDate(LocalDate.now().plusMonths(1))
                        .build();
                member.addFcmToken(newFcmToken);
                fcmTokenResDTO = new FcmTokenResDTO(fcmTokenRepository.save(newFcmToken));
            }
            else {  // 토큰 소유자가 요청 유저와 같을 경우 정보만 갱신
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
     * @param memberId 유저 로그아웃 시 요청한 유저의 특정 기기 fcm 토큰 삭제
     * @return 토큰 삭제 여부 리턴
     * @throws IllegalArgumentException 유저 계정이 유효하지 않을 때 exception
     */
    @Transactional
    public boolean deleteFcmToken(int memberId, String token) {
        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessageEnum.USER_NOT_EXIST.getMessage()));

        List<FcmToken> fcmToken = member.getFcmTokenList().stream().filter(t ->
                t.getToken().equals(token)).collect(Collectors.toList());

        if (!fcmToken.isEmpty()) {
            fcmTokenRepository.deleteById(fcmToken.get(0).getId());
            member.removeFcmToken(fcmToken.get(0));
            return true;
        }
        return false;
    }

    /**
     * @param campsiteUuid : 알람 신청/해제할 캠핑장 uuid
     * @param memberId : 해당 서비스 요청 계정 id
     * @param token : 특정 기기의 fcm token
     * @return FcmTokenResDTO : 해당 유저가 특정 토큰에 대해 db에서 가지고 있는 정보
     *
     * 유저가 해당 토큰을 가지고 있을 경우, 해당 토큰 수정 & refresh
     * 유저가 해당 토큰을 가지고 있지 않을 경우, 기존 토큰 삭제 시도. 토큰 생성 후 요청 유저에 할당
     */
    @Transactional
    public FcmTokenResDTO subscribeCamp(String campsiteUuid, int memberId, String token){
        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessageEnum.USER_NOT_EXIST.getMessage()));
        FcmToken fcmToken = fcmTokenRepository.findByToken(token).orElse(null);

        if (fcmToken == null){
            FcmToken newFcmToken = FcmToken.builder()
                    .campsiteUuid(campsiteUuid)
                    .token(token)
                    .member(member)
                    .expiredDate(LocalDate.now().plusMonths(1)).build();
            member.addFcmToken(newFcmToken);
            return FcmTokenResDTO.builder().fcmToken(newFcmToken).build();
        }
        else{
            if (!member.getFcmTokenList().contains(fcmToken)){
                Member holder = memberRepository.findMemberByIdAndExpiredIsFalse(fcmToken.getMember().getId()).get();
                fcmTokenRepository.deleteById(fcmToken.getId());
                holder.removeFcmToken(fcmToken);

                FcmToken newFcmToken = FcmToken.builder()
                        .campsiteUuid(campsiteUuid)
                        .token(token)
                        .member(member)
                        .expiredDate(LocalDate.now().plusMonths(1)).build();
                member.addFcmToken(newFcmToken);

                return FcmTokenResDTO.builder().fcmToken(fcmTokenRepository.save(newFcmToken)).build();
            }
            System.out.println("져거");
            fcmToken.subscribeCamp(campsiteUuid);
            fcmToken.refreshFcmToken(token);
            FcmToken savedToken = fcmTokenRepository.save(fcmToken);
            return FcmTokenResDTO.builder().fcmToken(savedToken).build();
        }
    }

    public FcmTokenResDTO findMyFcmToken(int memberId, String token){

        FcmToken fcmToken = fcmTokenRepository.findByMember_IdAndToken(memberId, token).orElse(null);

        if (fcmToken == null){
            Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId).orElseThrow(() ->
                    new NoSuchElementException(ErrorMessageEnum.USER_NOT_EXIST.getMessage()));
            fcmToken = FcmToken.builder().member(member).token(token).expiredDate(LocalDate.now().plusMonths(1)).build();

            member.addFcmToken(fcmToken);
            FcmToken savedToken = fcmTokenRepository.save(fcmToken);
            return FcmTokenResDTO.builder().fcmToken(savedToken).build();
        }
        return FcmTokenResDTO.builder().fcmToken(fcmToken).build();
    }
}
