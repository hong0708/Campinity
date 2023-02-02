package com.ssafy.campinity.core.service;

import com.ssafy.campinity.core.dto.LatLngDTO;
import com.ssafy.campinity.core.entity.message.Message;
import org.springframework.stereotype.Service;
import com.ssafy.campinity.core.dto.MessageReqDTO;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
public interface MessageService {

    Message createMessage(MessageReqDTO messageReqDTO, int memberId);

    List<Message> getMessagesByCampsiteUuidBetweenLatLng(String campsiteUuid, LatLngDTO latLngDTO);

    Message getMessage(String messageId);

    void deleteMessage(String messageId, int memberId) throws FileNotFoundException;

    boolean likeMessage(int memberId, String messageUuid);
}