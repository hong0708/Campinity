package com.ssafy.campinity.api.controller;

import com.ssafy.campinity.api.config.security.jwt.MemberDetails;
import com.ssafy.campinity.core.dto.LatLngDTO;
import com.ssafy.campinity.core.dto.MessageLikeDTO;
import com.ssafy.campinity.core.dto.MessageReqDTO;
import com.ssafy.campinity.core.dto.MessageResDTO;
import com.ssafy.campinity.core.entity.message.Message;
import com.ssafy.campinity.core.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/messages")
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<MessageResDTO> createMessage(
            @AuthenticationPrincipal MemberDetails memberDetails,
            MessageReqDTO messageReqDTO){

        Message message = messageService.createMessage(messageReqDTO, memberDetails.getMember().getId());

        MessageResDTO messageResDTO = new MessageResDTO(message, memberDetails.getMember().getUuid());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Location", "/api/v2/messages/" + message.getUuid().toString());

        return ResponseEntity.status(HttpStatus.CREATED).headers(responseHeaders).body(messageResDTO);

    }

    @GetMapping("/{campsiteId}/scope")
    public ResponseEntity<List<MessageResDTO>> getMessagesByCampsiteIdLatLngBetweenScope(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable String campsiteId,
            LatLngDTO latLngDTO){

        List<Message> messages = messageService.getMessagesByCampsiteUuidBetweenLatLng(campsiteId, latLngDTO);
        List<MessageResDTO> messageResDTOList = messages.stream().map(message -> new MessageResDTO(message, memberDetails.getMember().getUuid())).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(messageResDTOList);

    }

    @GetMapping("/{messageId}")
    public ResponseEntity<Object> getMessage(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable String messageId){

        Message message = messageService.getMessage(messageId);
        MessageResDTO messageResDTO = new MessageResDTO(message, memberDetails.getMember().getUuid());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Location", "/api/v2/messages/" + message.getUuid().toString());

        return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(messageResDTO);

    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Object> deleteMessage(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable String messageId) throws FileNotFoundException {

        messageService.deleteMessage(messageId, memberDetails.getMember().getUuid());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

    }


    @PutMapping("/like/{messageId}")
    public ResponseEntity<MessageLikeDTO> likeMessage(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable String messageId){

        try {
            boolean likeCheck = messageService.likeMessage(memberDetails.getMember().getId(), messageId);
            MessageLikeDTO messageLikeDTO = MessageLikeDTO.builder().likeCheck(likeCheck).build();
            return ResponseEntity.status(HttpStatus.OK).body(messageLikeDTO);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
