package com.ssafy.campinity.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.squareup.okhttp.*;
import com.ssafy.campinity.core.dto.FcmMessageToManyDTO;
import com.ssafy.campinity.core.dto.FcmMessageToOneDTO;
import com.ssafy.campinity.core.service.FcmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class FcmServiceImpl implements FcmService {

    @Value("${fcm.certification}")
    private static String FIREBASE_CERTIFICATION_KEY_PATH;
    @Value("${fcm.url}")
    private static String FCM_URL;

    private final ObjectMapper objectMapper;

    // targetToken으로 fcm message 전송
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
    public void sendMessageToMany(
            String campsiteGroup
            , String FcmMessageId
            , String title
            , String body) throws IOException{

        String message = makeMessageToMany(campsiteGroup, FcmMessageId, title, body);

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
    private String makeMessageToMany(String campsiteGroup
            , String fcmMessageId
            , String title
            , String body) throws JsonProcessingException {

        FcmMessageToManyDTO fcmMessageToManyDTO = FcmMessageToManyDTO.builder()
                .message(FcmMessageToManyDTO.Message.builder()
                        .topic(campsiteGroup)
                        .notification(FcmMessageToManyDTO.Notification.builder()
                                .title(title)
                                .body(body)
                                .build())
                        .data(FcmMessageToManyDTO.FcmData.builder()
                                .campsiteGroupId(campsiteGroup)
                                .MessageId(fcmMessageId)
                                .build())
                        .build())
                .validate_only(false)
                .build();
        return objectMapper.writeValueAsString(fcmMessageToManyDTO);
    }

    // FCM에 요청 시 header 담을 accessToken
    private static String getAccessToken() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new FileInputStream(FIREBASE_CERTIFICATION_KEY_PATH))
                .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));

        // FCM accessToken 생성
        googleCredentials.refreshIfExpired();

        // GoogleCredential의 getAccessToken으로 토큰 받아온 뒤, getTokenValue로 최종적으로 받음
        // REST API로 FCM에 push 요청 보낼 때 Header에 설정하여 인증을 위해 사용
        return googleCredentials.getAccessToken().getTokenValue();
    }
}
