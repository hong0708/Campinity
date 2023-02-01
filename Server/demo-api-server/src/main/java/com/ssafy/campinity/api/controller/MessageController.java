package com.ssafy.campinity.api.controller;

import com.ssafy.campinity.api.config.security.jwt.MemberDetails;
import com.ssafy.campinity.api.dto.res.MessageDeleteDTO;
import com.ssafy.campinity.core.dto.LatLngDTO;
import com.ssafy.campinity.core.dto.MessageLikeDTO;
import com.ssafy.campinity.core.dto.MessageReqDTO;
import com.ssafy.campinity.core.dto.MessageResDTO;
import com.ssafy.campinity.core.entity.message.Message;
import com.ssafy.campinity.core.service.MessageService;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;


@Api(tags = "쪽지(리뷰 및 자유)관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/messages")
public class MessageController {

    private final MessageService messageService;

    @ApiResponses({
            @ApiResponse(code = 201, message = "쪽지 생성이 성공했을 때 응답"),
            @ApiResponse(code = 400, message = "입력 데이터 부적합(파라미터 이미지 파일 확장자, 타입 및 입력값 부적절 시 응답"),
            @ApiResponse(code = 401, message = "accessToken 부적합 시 응답"),
    })
    @ApiOperation(value = "쪽지를 생성 및 쪽지 객체 반환하는 API")
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResDTO> createMessage(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Parameter(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            MessageReqDTO messageReqDTO){

        Message message = messageService.createMessage(messageReqDTO, memberDetails.getMember().getId());

        MessageResDTO messageResDTO = new MessageResDTO(message, memberDetails.getMember().getUuid());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Location", "/api/v2/messages/" + message.getUuid().toString());

        return ResponseEntity.status(HttpStatus.CREATED).headers(responseHeaders).body(messageResDTO);

    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "캠핑장별 범위 내에 쪽지 조회 성공 시 응답"),
            @ApiResponse(code = 400, message = "입력 데이터 부적합(파라미터 타입 및 입력값 부적절 시 응답"),
            @ApiResponse(code = 401, message = "accessToken 부적합 시 응답"),
    })
    @ApiOperation(value = "캠핑장별 범위 내에 쪽지 조회 API")
    @GetMapping("/{campsiteId}/scope")
    public ResponseEntity<List<MessageResDTO>> getMessagesByCampsiteIdLatLngBetweenScope(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @ApiParam(value = "캠핑장 식별 아이디", required = true, type = "String")
            @PathVariable String campsiteId,
            LatLngDTO latLngDTO){

        List<Message> messages = messageService.getMessagesByCampsiteUuidBetweenLatLng(campsiteId, latLngDTO);
        List<MessageResDTO> messageResDTOList = messages.stream().map(message -> new MessageResDTO(message, memberDetails.getMember().getUuid())).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(messageResDTOList);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "쪽지 상세 조회 성공 시 응답"),
            @ApiResponse(code = 400, message = "입력 데이터 부적합(파라미터 타입 및 입력값 부적절 시 응답"),
            @ApiResponse(code = 401, message = "accessToken 부적합 시 응답"),
    })
    @ApiOperation(value = "쪽지 상세 조회 API")
    @GetMapping("/{messageId}")
    public ResponseEntity<MessageResDTO> getMessage(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @ApiParam(value = "쪽지 식별 아이디", required = true, type = "String")
            @PathVariable String messageId){

        Message message = messageService.getMessage(messageId);
        MessageResDTO messageResDTO = new MessageResDTO(message, memberDetails.getMember().getUuid());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Location", "/api/v2/messages/" + message.getUuid().toString());

        return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(messageResDTO);

    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "쪽지 삭제 성공 시 응답"),
            @ApiResponse(code = 400, message = "삭제권한이 없거나 쪽지 식별아이디 값 부적절 시 응답"),
            @ApiResponse(code = 401, message = "accessToken 부적합 시 응답"),
    })
    @ApiOperation(value = "쪽지 삭제 API")
    @DeleteMapping("/{messageId}")
    public ResponseEntity<MessageDeleteDTO> deleteMessage(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @ApiParam(value = "쪽지 식별 아이디", required = true, type = "String")
            @PathVariable String messageId) throws FileNotFoundException {

        messageService.deleteMessage(messageId, memberDetails.getMember().getUuid());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(MessageDeleteDTO.builder().messageId(messageId).build());
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "쪽지 좋아요 성공 시 응답"),
            @ApiResponse(code = 400, message = "쪽지 식별 아이디 값 부적절 시 응답"),
            @ApiResponse(code = 401, message = "accessToken 부적합 시 응답"),
    })
    @ApiOperation(value = "쪽지 좋아요/좋아요 취소 API")
    @PutMapping("/like/{messageId}")
    public ResponseEntity<MessageLikeDTO> likeMessage(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @ApiParam(value = "쪽지 식별 아이디", required = true, type = "String")
            @PathVariable String messageId){

            boolean likeCheck = messageService.likeMessage(memberDetails.getMember().getId(), messageId);
            MessageLikeDTO messageLikeDTO = MessageLikeDTO.builder().likeCheck(likeCheck).build();
            return ResponseEntity.status(HttpStatus.OK).body(messageLikeDTO);

    }
}
