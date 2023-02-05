package com.ssafy.campinity.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.squareup.okhttp.*;
import com.ssafy.campinity.core.dto.FcmMessageReqDTO;
import com.ssafy.campinity.core.dto.FcmMessageToManyDTO;
import com.ssafy.campinity.core.dto.FcmMessageToOneDTO;
import com.ssafy.campinity.core.entity.fcm.FcmMessage;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.repository.fcm.FcmMessageRepository;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import com.ssafy.campinity.core.service.FcmMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class FcmMessageServiceImpl implements FcmMessageService {

    private final String GOOGLE_APPLICATION_CREDENTIALS = "firebase/campinity-5ff94-firebase-adminsdk-a0uem-64c6576e75.json";
    private final String FCM_URL = "https://fcm.googleapis.com/v1/projects/campinity-5ff94/messages:send";
    private final String FIREBASE_SCOPE = "https://www.googleapis.com/auth/cloud-platform";
    private final MemberRepository memberRepository;
    private final FcmMessageRepository fcmMessageRepository;

    private final ObjectMapper objectMapper;


    // target Token으로 fcm message 전송
    @Override
    public void sendMessageToOne(String targetToken, String title, String body) throws IOException{
        String message = makeMessageToOne(targetToken, title, body);

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), message);
        Request request = new Request.Builder()
                .url(FCM_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = okHttpClient.newCall(request).execute();
        log.info(response.body().string());
    }

    // 파라미터를 FCM이 요구하는 body 형태의 message return
    private String makeMessageToOne(String targetToken, String title, String body) throws JsonProcessingException {
        FcmMessageToOneDTO fcmMessageToOneDTO = FcmMessageToOneDTO.builder()
                .message(FcmMessageToOneDTO.Message.builder()
                        .token(targetToken)
                        .notification(FcmMessageToOneDTO.Notification.builder()
                                .title(title)
                                .body(body)
                                .build())
                        .build())
                .validate_only(false)
                .build();
        return objectMapper.writeValueAsString(fcmMessageToOneDTO);
    }

    // target campsite group으로 fcm message 단체 전송
    @Override
    public void sendMessageToTopic(
            FcmMessageReqDTO reqContent,
            int memberId) throws IOException{

        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId).orElseThrow(IllegalArgumentException::new);
        FcmMessage fcmMessage = FcmMessage.builder()
                .member(member)
                .title(reqContent.getTitle())
                .body(reqContent.getBody())
                .hiddenBody(reqContent.getHiddenBody())
                .build();

        FcmMessage savedFcmMessage = fcmMessageRepository.save(fcmMessage);

        String messageToFcm = makeMessageToMany(
                member.getFcmTokenList().get(0).getCampsiteUuid(),
                savedFcmMessage.getUuid().toString(),
                reqContent.getTitle(),
                reqContent.getBody());

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), messageToFcm);
        Request request = new Request.Builder()
                .url(FCM_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = okHttpClient.newCall(request).execute();
        log.info(response.body().string());
    }

    // 파라미터를 FCM이 요구하는 body 형태의 message return
    private String makeMessageToMany(String campsiteGroup,
                                     String fcmMessageId,
                                     String title,
                                     String body) throws JsonProcessingException {

        FcmMessageToManyDTO fcmMessageToManyDTO = FcmMessageToManyDTO.builder()
                .message(FcmMessageToManyDTO.Message.builder()
                        .topic(campsiteGroup)
                        .notification(FcmMessageToManyDTO.Notification.builder()
                                .title(title)
                                .body(body)
                                .build())
                        .data(FcmMessageToManyDTO.FcmData.builder()
                                .campsiteGroupId(campsiteGroup)
                                .fcmMessageId(fcmMessageId)
                                .build())
                        .build())
                .validate_only(false)
                .build();
        return objectMapper.writeValueAsString(fcmMessageToManyDTO);
    }

    // 자격 증명을 사용하여  FCM에 요청 시 header 담을 액세스 토큰 생성
    private String getAccessToken() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(GOOGLE_APPLICATION_CREDENTIALS).getInputStream())
                .createScoped(Arrays.asList(FIREBASE_SCOPE));

        // FCM accessToken 생성
        googleCredentials.refreshIfExpired();

        // GoogleCredential의 getAccessToken으로 토큰 받아온 뒤, getTokenValue로 최종적으로 받음
        // REST API로 FCM에 push 요청 보낼 때 Header에 설정하여 인증을 위해 사용
        return googleCredentials.getAccessToken().getTokenValue();
    }
}
