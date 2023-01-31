package com.ssafy.campinity.core.service;

import java.io.IOException;

public interface FcmMessageService {

    void sendMessageToOne(String targetToken, String title, String body) throws IOException;
    void sendMessageToMany(String campsiteGroup, String FcmMessageId, String title, String body) throws IOException;
}
