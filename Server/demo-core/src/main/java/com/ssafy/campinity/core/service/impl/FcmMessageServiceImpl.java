package com.ssafy.campinity.core.service.impl;

import com.google.firebase.messaging.*;
import com.ssafy.campinity.core.dto.FcmMessageReqDTO;
import com.ssafy.campinity.core.dto.FcmReplyDTO;
import com.ssafy.campinity.core.dto.LastFcmReqDTO;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.fcm.FcmMessage;
import com.ssafy.campinity.core.entity.fcm.FcmToken;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.exception.FcmMessagingException;
import com.ssafy.campinity.core.repository.campsite.CampsiteRepository;
import com.ssafy.campinity.core.repository.fcm.FcmMessageRepository;
import com.ssafy.campinity.core.repository.fcm.FcmTokenRepository;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import com.ssafy.campinity.core.service.ChatService;
import com.ssafy.campinity.core.service.FcmMessageService;
import com.ssafy.campinity.core.utils.ErrorMessageEnum;
import com.sun.xml.bind.v2.model.runtime.RuntimeTypeInfoSet;
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
    private final CampsiteRepository campsiteRepository;
    private final ChatService chatService;


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

        return makeMessageToMany(memberId, savedFcm);
    }

    // fcm에 토큰에 push 요청 및 invalid token 삭제
    private int makeMessageToMany(int memberId, FcmMessage savedFcm) {

        List<String> fcmTokenList = fcmTokenRepository.findTop500ByCampsiteUuidAndMember_IdIsNot(savedFcm.getCampsiteUuid(), memberId).stream()
                .map(token -> token.getToken()).collect(Collectors.toList());

        if (fcmTokenList.size() == 0) { return 0; }

        MulticastMessage fcmMessage = MulticastMessage.builder()
                .addAllTokens(fcmTokenList)
                .setNotification(Notification.builder().setTitle(savedFcm.getTitle()).setBody(savedFcm.getBody()).build())
                .putData("fcmMessageId", savedFcm.getUuid().toString())
                .putData("type", "RECEIVE_HELP_NOTE")
                .putData("title", savedFcm.getTitle())
                .putData("body", savedFcm.getBody())
                .setAndroidConfig(AndroidConfig.builder()
                        .setNotification(AndroidNotification.builder().setClickAction("RECEIVE_HELP_NOTE").build())
                        .setPriority(AndroidConfig.Priority.HIGH)
                        .build())
                .build();

        BatchResponse response = firebaseMulticastMessaging(fcmMessage);
        int resSize = response.getResponses().size();
        List<String> invalidTokens = new ArrayList<>();

        for (int i = 0; i < resSize; i++)
            if(!response.getResponses().get(i).isSuccessful()) invalidTokens.add(fcmTokenList.get(i));

        if (invalidTokens.size() != 0) deleteInvalidToken(invalidTokens);

        return response.getSuccessCount();
    }

    @Transactional
    @Override
    public int replyToFcm(int memberId, FcmReplyDTO fcmReplyDTO) {

        FcmMessage fcmMessage = fcmMessageRepository.findByUuidAndExpiredIsFalse(UUID.fromString(fcmReplyDTO.getFcmMessageId()))
                .orElse(null);
        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId)
                .orElseThrow(() -> new NoSuchElementException(ErrorMessageEnum.USER_NOT_EXIST.getMessage()));

        int successfulSendCnt = 0;
        if (fcmMessage == null) return successfulSendCnt;
        String campsiteId = fcmMessage.getCampsiteUuid();

        Campsite campsite = campsiteRepository.findByUuid(UUID.fromString(fcmMessage.getCampsiteUuid())).orElseThrow(() ->
                new NoSuchElementException(ErrorMessageEnum.CAMPSITE_NOT_FOUND.getMessage()));


        // 채팅방 생성 메서드
        chatService.createChatRoom(campsiteId, member.getUuid().toString(), fcmMessage.getMember().getUuid().toString(), fcmMessage.getBody());

        List<String> senderTokens = fcmMessage.getMember().getFcmTokenList().stream().filter(token ->
                        token.getCampsiteUuid() != null && token.getCampsiteUuid().equals(campsiteId))
                .map(FcmToken::getToken)
                .collect(Collectors.toList());

        if (senderTokens.size() == 0) throw new FcmMessagingException("에러로 인해 이벤트가 만료됐습니다.");

        StringBuffer body = new StringBuffer();
        body.append(fcmMessage.getMember().getName() + " 님!\n");
        body.append(String.format("도움이 필요한 캠퍼, %s 님을 찾았습니다 :)\n클릭해서 %s 님과 대화해 보세요.", member.getName(), member.getName()));

        successfulSendCnt += makeMessageToAppointee(fcmReplyDTO.getFcmToken(), fcmMessage, senderTokens);
        successfulSendCnt += makeMessageToSender(senderTokens, "매칭 성공 !", body.toString());

        fcmMessage.expired();
        fcmMessage.appointMember(fcmReplyDTO.getFcmToken());
        fcmMessageRepository.save(fcmMessage);

        return successfulSendCnt;
    }

    private int makeMessageToAppointee(String appointeeToken, FcmMessage data, List<String> senderFcmTokens) {

        MulticastMessage fcmMessage = MulticastMessage.builder()
                .addToken(appointeeToken)
                .setNotification(Notification.builder().setTitle(data.getTitle()).setBody(data.getHiddenBody()).build())
                .putData("title", "도움을 줄 캠퍼의 위치입니다.\n클릭해서 캠퍼와 대화해보세요!")
                .putData("body", data.getHiddenBody())
                .putData("type", "CHATTING")
                .putData("longitude", data.getLongitude().toString())
                .putData("latitude", data.getLatitude().toString())
//                .putData("senderFcmTokenList", senderFcmTokens.toString())
                .setAndroidConfig(AndroidConfig.builder()
                        .setNotification(AndroidNotification.builder().setClickAction("CHATTING").build())
                        .setPriority(AndroidConfig.Priority.HIGH)
                        .build())
                .build();

        BatchResponse response = firebaseMulticastMessaging(fcmMessage);
        if(!response.getResponses().get(0).isSuccessful()) deleteInvalidToken(List.of(appointeeToken));

        return response.getSuccessCount();
    }

    private int makeMessageToSender(List<String> senderTokens, String title, String body) {

        MulticastMessage fcmMessage = MulticastMessage.builder()
                .addAllTokens(senderTokens)
                .setNotification(Notification.builder().setTitle(title).setBody(body).build())
                .putData("title", title)
                .putData("body", body)
                .putData("type", "CHATTING")
                .setAndroidConfig(AndroidConfig.builder()
                        .setNotification(AndroidNotification.builder().setClickAction("CHATTING").build())
                        .setPriority(AndroidConfig.Priority.HIGH)
                        .build())
                .build();

        BatchResponse response = firebaseMulticastMessaging(fcmMessage);
        int resSize = response.getResponses().size();
        List<String> invalidTokens = new ArrayList<>();

        for (int i = 0; i < resSize; i++)
            if(!response.getResponses().get(i).isSuccessful()) invalidTokens.add(senderTokens.get(i));

        if (invalidTokens.size() != 0) deleteInvalidToken(invalidTokens);

        return response.getSuccessCount();
    }

    @Override
    public int sendLastFcmMessage(int memberId, LastFcmReqDTO lastFcmReqDTO) {

        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId)
                .orElseThrow(() -> new NoSuchElementException(ErrorMessageEnum.USER_NOT_EXIST.getMessage()));

        MulticastMessage fcmMessage = MulticastMessage.builder()
                .addAllTokens(lastFcmReqDTO.getSenderTokens())
                .setNotification(Notification.builder().setTitle("매칭된 캠퍼로부터 온 메세지입니다 :)").setBody(lastFcmReqDTO.getBody()).build())
                .putData("title", "매칭된 캠퍼로부터 온 메세지입니다 :)")
                .putData("body", lastFcmReqDTO.getBody())
                .putData("type", "COMMUNITY")
                .setAndroidConfig(AndroidConfig.builder()
                        .setNotification(AndroidNotification.builder().setClickAction("COMMUNITY").build())
                        .setPriority(AndroidConfig.Priority.HIGH)
                        .build())
                .build();

        BatchResponse response = firebaseMulticastMessaging(fcmMessage);
        int resSize = response.getResponses().size();
        List<String> invalidTokens = new ArrayList<>();

        for (int i = 0; i < resSize; i++)
            if(!response.getResponses().get(i).isSuccessful()) invalidTokens.add(lastFcmReqDTO.getSenderTokens().get(i));

        if (invalidTokens.size() != 0) deleteInvalidToken(invalidTokens);

        return response.getSuccessCount();
    }

    private BatchResponse firebaseMulticastMessaging(MulticastMessage fcmMessage)  {
        try {
            return FirebaseMessaging.getInstance().sendMulticast(fcmMessage);
        }
        catch(Exception e){
            logger.warn(e.getMessage());
            throw new FcmMessagingException(ErrorMessageEnum.FCMEMSSAGING_ERROR.getMessage());
        }
    }

    //  유효하지 않는 토큰 삭제
    private void deleteInvalidToken(List<String> invalidTokens){
        List<FcmToken> fcmTokens = fcmTokenRepository.findAllByTokenIn(invalidTokens);
        fcmTokenRepository.deleteAllInBatch(fcmTokens);
    }
}
