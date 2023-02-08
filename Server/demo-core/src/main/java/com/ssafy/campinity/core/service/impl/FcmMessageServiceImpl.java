package com.ssafy.campinity.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.messaging.*;
import com.ssafy.campinity.core.dto.FcmMessageReqDTO;
import com.ssafy.campinity.core.dto.FcmMessageToManyDTO;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FcmMessageServiceImpl implements FcmMessageService {

    private static final Logger logger = (Logger) LogManager.getLogger(FcmMessageServiceImpl.class);
    private final String GOOGLE_APPLICATION_CREDENTIALS = "firebase/campinity-5ff94-firebase-adminsdk-a0uem-64c6576e75.json";
    private final String FCM_URL = "https://fcm.googleapis.com/v1/projects/campinity-5ff94/messages:send";
    private final String FIREBASE_SCOPE = "https://www.googleapis.com/auth/cloud-platform";
    private final MemberRepository memberRepository;
    private final FcmMessageRepository fcmMessageRepository;
    private final FcmTokenRepository fcmTokenRepository;
    private final ObjectMapper objectMapper;


    // target Token으로 fcm message 전송
    @Override
    public int sendMessageToMany(int memberId, FcmMessageReqDTO req) {

        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId)
                .orElseThrow(() -> new NoSuchElementException(ErrorMessageEnum.MESSAGE_NOT_EXIST.getMessage()));

        FcmMessage fcmMessage = FcmMessage.builder()
                .uuid(UUID.randomUUID())
                .title(req.getTitle())
                .body(req.getBody())
                .hiddenBody(req.getHiddenBody())
                .longitude(req.getLongitude())
                .latitude(req.getLatitude())
                .member(member)
                .build();
        fcmMessageRepository.save(fcmMessage);

        int successfulSendCnt = 0;
        try { successfulSendCnt = makeMessageToMany(req.getCampsiteId(), req.getTitle(), req.getBody()); }
        catch (FirebaseMessagingException e){
            logger.warn(e.getMessage());
            throw new FcmMessagingException("push message 서비스가 중단되었습니다.");
        }

        return successfulSendCnt;
    }

    // 파라미터를 FCM이 요구하는 body 형태의 message return
    private int makeMessageToMany(String campsiteId, String title, String body) throws FirebaseMessagingException {

        List<String> fcmTokenList = fcmTokenRepository.findAllByCampsiteUuid(campsiteId).stream()
                .map(token -> token.getToken()).collect(Collectors.toList());

        MulticastMessage fcmMessage = MulticastMessage.builder()
                .addAllTokens(fcmTokenList)
                .setNotification(Notification.builder().setTitle(title).setBody(body).build())
                .putData("redisId", campsiteId)
                .build();

        BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(fcmMessage);
        int resSize = response.getResponses().size();
        List<String> invaildTokens = new ArrayList<>();

        for (int i = 0; i < resSize; i++)
            if(!response.getResponses().get(i).isSuccessful()) invaildTokens.add(fcmTokenList.get(i));

        if (invaildTokens.size() != 0) deleteInvalidToken(invaildTokens);

        return response.getSuccessCount();
    }

    //  유효하지 않는 토큰 삭제
    private void deleteInvalidToken(List<String> invalidTokens){
        List<FcmToken> fcmTokens = fcmTokenRepository.findAllByTokenIn(invalidTokens);
        fcmTokenRepository.deleteAllInBatch(fcmTokens);
    }
}
