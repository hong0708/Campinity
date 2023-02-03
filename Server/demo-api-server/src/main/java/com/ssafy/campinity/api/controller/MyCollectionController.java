package com.ssafy.campinity.api.controller;

import com.ssafy.campinity.api.config.security.jwt.MemberDetails;
import com.ssafy.campinity.api.dto.res.MyCollectionDeleteDTO;
import com.ssafy.campinity.core.dto.MyCollectionReqDTO;
import com.ssafy.campinity.core.dto.MyCollectionResDTO;
import com.ssafy.campinity.core.entity.MyCollection.MyCollection;
import com.ssafy.campinity.core.service.MyCollectionService;
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
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "컬렉션 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v5/my-collections")
public class MyCollectionController {

    private final MyCollectionService myCollectionService;

    @ApiResponses({
            @ApiResponse(code = 201, message = "컬렉션 생성이 성공했을 때 응답"),
            @ApiResponse(code = 400, message = "입력 데이터 부적합(파라미터 이미지 파일 확장자, 타입 및 입력값 부적절 시 응답"),
            @ApiResponse(code = 401, message = "accessToken 부적합 시 응답"),
    })
    @ApiOperation(value = "컬렉션 생성 및 컬렉션 객체 반환하는 API")
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MyCollectionResDTO> createMyCollection(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Parameter(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            MyCollectionReqDTO myCollectionReqDTO) {

        MyCollection myCollection = myCollectionService.createMyCollection(myCollectionReqDTO, memberDetails.getMember().getId());

        MyCollectionResDTO myCollectionResDTO = new MyCollectionResDTO(myCollection);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Location", "/api/v5/my-collections/" + myCollection.getUuid().toString());

        return ResponseEntity.status(HttpStatus.CREATED).headers(responseHeaders).body(myCollectionResDTO);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "컬렉션 수정을 완료했을 때 응답"),
            @ApiResponse(code = 400, message = "입력 데이터 부적합(파라미터 이미지 파일 확장자, 타입 및 입력값 부적절 시 응답"),
            @ApiResponse(code = 401, message = "accessToken 부적합 시 응답"),
    })
    @ApiOperation(value = "컬렉션 수정 및 컬렉션 객체 반환하는 API")
    @PostMapping(value = "/{collectionId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MyCollectionResDTO> editMyCollection(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @ApiParam(value = "컬렉션 식별 아이디", required = true, type = "String")
            @PathVariable String collectionId,
            @Parameter(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            MyCollectionReqDTO myCollectionReqDTO) throws FileNotFoundException, UnsupportedEncodingException {

        MyCollection myCollection = myCollectionService.editMyCollection(myCollectionReqDTO, collectionId, memberDetails.getMember().getId());

        MyCollectionResDTO myCollectionResDTO = new MyCollectionResDTO(myCollection);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Location", "/api/v5/my-collections/" + myCollection.getUuid().toString());

        return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(myCollectionResDTO);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "컬렉션 리스트 조회를 성공했을 때 응답"),
            @ApiResponse(code = 400, message = "컬렉션 식별 아이디 값 부적절 시 응답"),
            @ApiResponse(code = 401, message = "accessToken 부적합 시 응답")
    })
    @ApiOperation(value = "컬렉션 리스트 조회 API")
    @GetMapping
    public ResponseEntity<List<MyCollectionResDTO>> getMyCollections(
            @AuthenticationPrincipal MemberDetails memberDetails
    ){
        List<MyCollection> myCollections = myCollectionService.getMyCollections(memberDetails.getMember().getId());
        List<MyCollectionResDTO> myCollectionResDTOList = myCollections.stream().map(MyCollectionResDTO::new).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(myCollectionResDTOList);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "컬렉션 상세 정보 조회를 성공했을 때 응답"),
            @ApiResponse(code = 400, message = "컬렉션 식별 아이디 값 부적절 시 응답"),
            @ApiResponse(code = 401, message = "accessToken 부적합 시 응답")
    })
    @ApiOperation(value = "컬렉션 상세 정보 조회 API")
    @GetMapping("/{collectionId}")
    public ResponseEntity<Object> getMyCollection(
            @ApiParam(value = "컬렉션 식별 아이디", required = true, type = "String")
            @PathVariable String collectionId){

        MyCollection myCollection = myCollectionService.getMyCollection(collectionId);
        MyCollectionResDTO myCollectionResDTO = new MyCollectionResDTO(myCollection);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Location", "/api/v5/my-collections/" + myCollection.getUuid().toString());

        return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(myCollectionResDTO);

    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "컬렉션 리스트 삭제를 성공했을 때 응답"),
            @ApiResponse(code = 400, message = "삭제권한이 없거나 쪽지 식별아이디 값 부적절 시 응답"),
            @ApiResponse(code = 401, message = "accessToken 부적합 시 응답")
    })
    @DeleteMapping("/{collectionId}")
    public ResponseEntity<MyCollectionDeleteDTO> deleteCollection(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @ApiParam(value = "컬렉션 식별 아이디", required = true, type = "String")
            @PathVariable String collectionId) throws FileNotFoundException {

        myCollectionService.deleteMyCollection(collectionId, memberDetails.getMember().getId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(MyCollectionDeleteDTO.builder().collectionId(collectionId).build());
    }


}
