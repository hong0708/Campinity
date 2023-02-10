package com.ssafy.campinity.core.service.impl;

import com.google.firebase.messaging.*;
import com.ssafy.campinity.core.dto.FcmMessageReqDTO;
import com.ssafy.campinity.core.dto.FcmReplyDTO;
import com.ssafy.campinity.core.entity.fcm.FcmMessage;
import com.ssafy.campinity.core.entity.fcm.FcmToken;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.exception.FcmMessagingException;
import com.ssafy.campinity.core.repository.fcm.FcmMessageRepository;
import com.ssafy.campinity.core.repository.fcm.FcmTokenRepository;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import com.ssafy.campinity.core.service.FcmMessageService;
import com.ssafy.campinity.core.utils.ErrorMessageEnum;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FcmMessageServiceImpl implements FcmMessageService {

    private static final Logger logger = (Logger) LogManager.getLogger(FcmMessageServiceImpl.class);
    private final MemberRepository memberRepository;
    private final FcmMessageRepository fcmMessageRepository;
    private final FcmTokenRepository fcmTokenRepository;


    // target Token으로 fcm message 전송
    @Override
    public int sendMessageToMany(int memberId, FcmMessageReqDTO req) {

        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId)
                .orElseThrow(() -> new NoSuchElementException(ErrorMessageEnum.MESSAGE_NOT_EXIST.getMessage()));

        FcmMessage fcmMessage = FcmMessage.builder()
                .campsiteUuid(req.getCampsiteId())
                .uuid(UUID.randomUUID())
                .title(req.getTitle())
                .body(req.getBody())
                .hiddenBody(req.getHiddenBody())
                .longitude(req.getLongitude())
                .latitude(req.getLatitude())
                .member(member)
                .build();
        FcmMessage savedFcm = fcmMessageRepository.save(fcmMessage);

        int successfulSendCnt = 0;
        try { successfulSendCnt = makeMessageToMany(memberId, savedFcm); }
        catch (FirebaseMessagingException e){
            logger.warn(e.getMessage());
            throw new FcmMessagingException("push message 서비스가 중단되었습니다.");
        }
        return successfulSendCnt;
    }

//    // fcm에 토큰에 push 요청 및 invalid token 삭제
//    private int makeMessageToMany(int memberId, FcmMessage savedFcm) throws FirebaseMessagingException {
//
//        List<String> fcmTokenList = fcmTokenRepository.findTop500ByCampsiteUuidAndMember_IdIsNot(savedFcm.getCampsiteUuid(), memberId).stream()
//                .map(token -> token.getToken()).collect(Collectors.toList());
//
//        if (fcmTokenList.size() == 0) {
//            return 0;
//        }
//        int successfulSendCnt = 0;
//        try { successfulSendCnt = makeMessageToMany(memberId, savedFcm); }
//        catch (FirebaseMessagingException e){
//            logger.warn(e.getMessage());
//            throw new FcmMessagingException("push message 서비스가 중단되었습니다.");
//        }
//        return successfulSendCnt;
//    }

    // fcm에 토큰에 push 요청 및 invalid token 삭제
    private int makeMessageToMany(int memberId, FcmMessage savedFcm) throws FirebaseMessagingException {

        List<String> fcmTokenList = fcmTokenRepository.findTop500ByCampsiteUuidAndMember_IdIsNot(savedFcm.getCampsiteUuid(), memberId).stream()
                .map(token -> token.getToken()).collect(Collectors.toList());
        System.out.println("search tokens : " + fcmTokenList.toString());

        MulticastMessage fcmMessage = MulticastMessage.builder()
                .addAllTokens(fcmTokenList)
                .setNotification(Notification.builder().setTitle(savedFcm.getTitle()).setBody(savedFcm.getBody()).build())
                .putData("fcmMessageId", savedFcm.getUuid().toString())
                .setAndroidConfig(AndroidConfig.builder().setPriority(AndroidConfig.Priority.HIGH).build())
                .build();
        return objectMapper.writeValueAsString(fcmMessageToOneDTO);
    }

    @Transactional
    @Override
    public int replyToFcm(int memberId, FcmReplyDTO fcmReplyDTO) {

        FcmMessage fcmMessage = fcmMessageRepository.findByUuidAndExpiredIsFalse(UUID.fromString(fcmReplyDTO.getFcmMessageId()))
                .orElseThrow(() -> new NoSuchElementException(ErrorMessageEnum.FCMMESSAGE_EXPIRED.getMessage()));

        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId)
                .orElseThrow(() -> new NoSuchElementException(ErrorMessageEnum.USER_NOT_EXIST.getMessage()));

        List<String> senderTokens = fcmMessage.getMember().getFcmTokenList().stream().filter(token ->
                        token.getCampsiteUuid().equals(fcmMessage.getCampsiteUuid()))
                .map(FcmToken::getToken)
                .collect(Collectors.toList());

        String senderBody = "";
        if (fcmMessage.getTitle() == "도움 주기"){ senderBody = "도움이 필요한 캠퍼를 찾았습니다 :)"; }
        else{ senderBody = "도와줄 캠퍼를 찾았습니다 :)"; }

        int successfulSendCnt = 0;
        try {
            successfulSendCnt += makeMessageToAppointee(fcmReplyDTO.getFcmToken(), fcmMessage);
            successfulSendCnt += makeMessageToSender(senderTokens, fcmMessage.getTitle(), senderBody);
        }
        catch (FirebaseMessagingException e){
            logger.warn(e.getMessage());
            throw new FcmMessagingException("push message 서비스가 중단되었습니다.");
        }

        fcmMessage.expired();
        fcmMessage.appointMember(fcmReplyDTO.getFcmToken());
        fcmMessageRepository.save(fcmMessage);

        return successfulSendCnt;
    }

    private int makeMessageToAppointee(String appointeeToken, FcmMessage data) throws FirebaseMessagingException {

        MulticastMessage fcmMessage = MulticastMessage.builder()
                .addToken(appointeeToken)
                .setNotification(Notification.builder().setTitle(data.getTitle()).setBody(data.getHiddenBody()).build())
                .putData("title", data.getTitle())
                .putData("body", data.getHiddenBody())
                .putData("longitude", data.getLongitude().toString())
                .putData("latitude", data.getLatitude().toString())
                .build();

        BatchResponse response = firebaseMulticastMessaging(fcmMessage);
        if(!response.getResponses().get(0).isSuccessful()) deleteInvalidToken(List.of(appointeeToken));

        return response.getSuccessCount();
    }

    private int makeMessageToSender(List<String> appointeeTokens, String title, String body) throws FirebaseMessagingException {

        MulticastMessage fcmMessage = MulticastMessage.builder()
                .addAllTokens(appointeeTokens)
                .setNotification(Notification.builder().setTitle(title).setBody(body).build())
                .putData("title", title)
                .putData("body", body)
                .build();

        BatchResponse response = firebaseMulticastMessaging(fcmMessage);
        int resSize = response.getResponses().size();
        List<String> invalidTokens = new ArrayList<>();

        for (int i = 0; i < resSize; i++)
            if(!response.getResponses().get(i).isSuccessful()) invalidTokens.add(appointeeTokens.get(i));

        if (invalidTokens.size() != 0) deleteInvalidToken(invalidTokens);

        return response.getSuccessCount();
    }


    private BatchResponse firebaseMulticastMessaging(MulticastMessage fcmMessage) throws FirebaseMessagingException {
        return FirebaseMessaging.getInstance().sendMulticast(fcmMessage);
    }

    //  유효하지 않는 토큰 삭제
    private void deleteInvalidToken(List<String> invalidTokens){
        List<FcmToken> fcmTokens = fcmTokenRepository.findAllByTokenIn(invalidTokens);
        fcmTokenRepository.deleteAllInBatch(fcmTokens);
    }
}
