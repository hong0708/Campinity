package com.ssafy.campinity.core.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.ssafy.campinity.core.dto.FcmMessageReqDTO;
import com.ssafy.campinity.core.dto.FcmReplyDTO;
import com.ssafy.campinity.core.dto.LastFcmReqDTO;

import java.util.List;

public interface FcmMessageService {
    int sendMessageToMany(int memberId, FcmMessageReqDTO req);
    int replyToFcm(int memberId, FcmReplyDTO fcmReplyDTO) ;
    int sendLastFcmMessage(int memberId, LastFcmReqDTO lastFcmReqDTO);
}
