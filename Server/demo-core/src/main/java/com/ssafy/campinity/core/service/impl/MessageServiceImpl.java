package com.ssafy.campinity.core.service.impl;

import com.ssafy.campinity.core.dto.LatLngDTO;
import com.ssafy.campinity.core.dto.MessageReqDTO;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.message.LikeMessage;
import com.ssafy.campinity.core.entity.message.Message;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.repository.campsite.CampsiteRepository;
import com.ssafy.campinity.core.repository.message.LikeMessageRepository;
import com.ssafy.campinity.core.repository.message.MessageRepository;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import com.ssafy.campinity.core.service.MessageService;
import com.ssafy.campinity.core.utils.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {

    private final CampsiteRepository campsiteRepository;
    private final MessageRepository messageRepository;
    private final ImageUtil imageUtil;
    private final MemberRepository memberRepository;
    private final LikeMessageRepository likeMessageRepository;

    @Transactional
    @Override
    public Message createMessage(MessageReqDTO messageReqDTO, String memberUuid) {

        Campsite campsite = campsiteRepository.findByUuid(messageReqDTO.getCampsiteId())
                .orElseThrow(IllegalArgumentException::new);
        Member member = memberRepository.findMemberByUuidAndExpiredIsFalse(UUID.fromString(memberUuid))
                .orElseThrow(IllegalArgumentException::new);

        String imagePath = "";
        try {
            imagePath = imageUtil.uploadImage(messageReqDTO.getFile(), "message");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        Message message = Message.builder()
                .uuid(UUID.randomUUID())
                .member(member)
                .campsite(campsite)
                .messageCategory(messageReqDTO.getMessageCategory())
                .imagePath(imagePath)
                .content(messageReqDTO.getContent())
                .latitude(messageReqDTO.getLatitude())
                .longitude(messageReqDTO.getLongitude())
                .build();

        try { return messageRepository.save(message); }
        catch (Exception e){ throw new IllegalArgumentException(e); }
    }

    @Transactional
    @Override
    public List<Message> getMessagesByCampsiteUuidBetweenLatLng(String campsiteUuid, LatLngDTO latLngDTO) {

        Campsite campsite = campsiteRepository.findByUuid(UUID.fromString(campsiteUuid))
                .orElseThrow(IllegalArgumentException::new);

        List<Message> messages = messageRepository
                .findMessagesByCampsiteAndLatitudeBetweenAndLongitudeBetweenAndExpiredIsFalse(
                        campsite,
                        latLngDTO.getBottomRightLat(),
                        latLngDTO.getTopLeftLat(),
                        latLngDTO.getTopLeftLng(),
                        latLngDTO.getBottomRightLng());

        if (messages.isEmpty()) return new ArrayList<>();
        return messages;
    }

    @Transactional
    @Override
    public Message getMessage(String messageId) {
        return messageRepository.findByUuidAndExpiredIsFalse(UUID.fromString(messageId))
                .orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    @Override
    public void deleteMessage(String messageId) {
        Message message = messageRepository.findByUuidAndExpiredIsFalse(UUID.fromString(messageId))
                .orElseThrow(IllegalArgumentException::new);
        messageRepository.deleteById(message.getId());
    }

    @Transactional
    @Override
    public boolean likeMessage(String userUuid, String messageUuid) {

        Member member = memberRepository.findMemberByUuidAndExpiredIsFalse(UUID.fromString(userUuid))
                .orElseThrow(IllegalArgumentException::new);
        Message message = messageRepository.findByUuidAndExpiredIsFalse(UUID.fromString(messageUuid))
                .orElseThrow(IllegalArgumentException::new);

        boolean likeCheck;
        Optional<LikeMessage> likeMessage = likeMessageRepository.findByMemberAndMessage(member, message);

        if (likeMessage.isPresent()) {
            likeMessageRepository.deleteByMemberAndMessage(member, message);
            likeCheck = false;
        }
        else {
            LikeMessage newLikeMessage = LikeMessage.builder()
                    .member(member).message(message).build();
            likeMessageRepository.save(newLikeMessage);
            likeCheck = true;
        }

        return likeCheck;
    }
}
