package com.ssafy.campinity.core.service;

import com.ssafy.campinity.core.dto.LatLngDTO;
import com.ssafy.campinity.core.entity.message.Message;
import org.springframework.stereotype.Service;
import com.ssafy.campinity.core.dto.MessageReqDTO;

import java.util.List;

@Service
public interface MessageService {

    Message createMessage(MessageReqDTO messageReqDTO, String memberUuid);

    List<Message> getMessagesByCampsiteUuidBetweenLatLng(String campsiteUuid, LatLngDTO latLngDTO);

    Message getMessage(String messageId);

    void deleteMessage(String messageId);

    boolean likeMessage(String userUuid, String messageUuid);
}