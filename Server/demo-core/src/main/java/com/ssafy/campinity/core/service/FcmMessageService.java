package com.ssafy.campinity.core.service;

import com.ssafy.campinity.core.dto.FcmMessageReqDTO;

import java.io.IOException;

public interface FcmMessageService {

    void sendMessageToOne(String targetToken, String title, String body) throws IOException;
    void sendMessageToTopic(FcmMessageReqDTO fcmMessageReqDTO, int memberId) throws IOException;
}
