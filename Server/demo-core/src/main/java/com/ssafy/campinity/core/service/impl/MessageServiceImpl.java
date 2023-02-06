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
import com.ssafy.campinity.core.utils.ErrorMessageEnum;
import com.ssafy.campinity.core.utils.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
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
    private static Logger logger = LogManager.getLogger(MessageServiceImpl.class);

    @Transactional
    @Override
    public Message createMessage(MessageReqDTO messageReqDTO, int memberId) {

        Campsite campsite = campsiteRepository.findByUuid(messageReqDTO.getCampsiteId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessageEnum.CAMPSITE_NOT_FOUND.getMessage()));
        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessageEnum.MESSAGE_NOT_EXIST.getMessage()));

        String imagePath = "";
        if (messageReqDTO.getFile().getSize() != 0){
            try {
                imagePath = imageUtil.uploadImage(messageReqDTO.getFile(), "message");
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
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
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessageEnum.CAMPSITE_NOT_FOUND.getMessage()));

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
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessageEnum.MESSAGE_NOT_EXIST.getMessage()));
    }

    @Transactional
    @Override
    public List<Message> getMyMessages(int memberId) {
        return messageRepository.findAllByMember_IdAndExpiredIsFalse(memberId);
    };

    @Transactional
    @Override
    public void deleteMessage(String messageId,int memberId) throws FileNotFoundException {
        Message message = messageRepository.findByUuidAndExpiredIsFalse(UUID.fromString(messageId))
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessageEnum.MESSAGE_NOT_EXIST.getMessage()));
        String imagePath = message.getImagePath();

        logger.info(imagePath);
        if (message.getMember().getId() == memberId) {
            if (!imagePath.isEmpty()){
                try {
                    imageUtil.removeImage(imagePath);
                }
                catch (Exception e){
                    throw new FileNotFoundException();
                }
            }
            messageRepository.deleteById(message.getId());
        }
    }

    @Override
    @Transactional
    public boolean likeMessage(int memberId, String messageUuid) {

        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessageEnum.MESSAGE_NOT_EXIST.getMessage()));
        Message message = messageRepository.findByUuidAndExpiredIsFalse(UUID.fromString(messageUuid))
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessageEnum.MESSAGE_NOT_EXIST.getMessage()));

        boolean likeCheck;
        Optional<LikeMessage> likeMessage = likeMessageRepository.findByMemberAndMessage(member, message);

        if (likeMessage.isPresent()) {
            likeMessageRepository.deleteByMemberAndMessage(member, message);
            message.removeLikeMessage(likeMessage.get());
            likeCheck = false;
        }
        else {
            LikeMessage newLikeMessage = LikeMessage.builder()
                    .member(member).message(message).build();
            likeMessageRepository.save(newLikeMessage);
            message.addLikeMessage(newLikeMessage);
            likeCheck = true;
        }

        return likeCheck;
    }
}
