package com.ssafy.campinity.core.service;

import com.ssafy.campinity.core.dto.FcmMessageReqDTO;
import com.ssafy.campinity.core.dto.FcmReplyDTO;

public interface FcmMessageService {
    int sendMessageToMany(int memberId, FcmMessageReqDTO req) ;
    int replyToFcm(int memberId, FcmReplyDTO fcmReplyDTO) ;
}
