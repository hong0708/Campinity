package com.ssafy.campinity.api.controller;

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
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/messages")
public class MessageController {

    private final MessageService messageService;
    private final String userUuid = "ae7766ef-a63c-4be3-ae7b-352112813328"; // 임시 테스트용 member uuid


    @PostMapping
    public ResponseEntity<MessageResDTO> createMessage(MessageReqDTO messageReqDTO){

        try {
            Message message = messageService.createMessage(messageReqDTO, userUuid);

            MessageResDTO messageResDTO = new MessageResDTO(message, userUuid);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Location", "/api/v2/messages/" + message.getUuid().toString());

            return ResponseEntity.status(HttpStatus.CREATED).headers(responseHeaders).body(messageResDTO);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{campsiteId}/scope")
    public ResponseEntity<List<MessageResDTO>> getMessagesByCampsiteIdLatLngBetweenScope(
            @PathVariable String campsiteId,
            LatLngDTO latLngDTO){
        try {
            List<Message> messages = messageService.getMessagesByCampsiteUuidBetweenLatLng(campsiteId, latLngDTO);
            List<MessageResDTO> messageResDTOList = messages.stream().map(message -> new MessageResDTO(message, userUuid)).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(messageResDTOList);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<Object> getMessage(@PathVariable String messageId){
        try {
            Message message = messageService.getMessage(messageId);
            MessageResDTO messageResDTO = new MessageResDTO(message, userUuid);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Location", "/api/v2/messages/" + message.getUuid().toString());

            return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(messageResDTO);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Object> deleteMessage(@PathVariable String messageId) {
        try {
            messageService.deleteMessage(messageId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PutMapping("/{memberId}/like/{messageId}")
    public ResponseEntity<MessageLikeDTO> likeMessage(
            @PathVariable String memberId,
            @PathVariable String messageId){

        try {
            boolean likeCheck = messageService.likeMessage(userUuid, messageId);
            MessageLikeDTO messageLikeDTO = MessageLikeDTO.builder().likeCheck(likeCheck).build();
            return ResponseEntity.status(HttpStatus.OK).body(messageLikeDTO);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
