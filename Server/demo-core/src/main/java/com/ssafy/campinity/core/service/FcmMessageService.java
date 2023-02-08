package com.ssafy.campinity.core.service;

import com.ssafy.campinity.core.dto.FcmMessageReqDTO;

import java.io.IOException;

public interface FcmMessageService {
    int sendMessageToMany(int memberId, FcmMessageReqDTO req) ;
}
