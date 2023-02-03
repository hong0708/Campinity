package com.ssafy.campinity.api.controller;


import com.ssafy.campinity.api.config.security.jwt.MemberDetails;
import com.ssafy.campinity.core.dto.MyCollectionResDTO;
import com.ssafy.campinity.core.entity.MyCollection.MyCollection;
import com.ssafy.campinity.core.service.MessageService;
import com.ssafy.campinity.core.service.MyCollectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "홈페이지 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v8/home")
public class HomeController {

    private final MyCollectionService myCollectionService;
    private final MessageService messageService;

    @ApiResponses({
            @ApiResponse(code = 200, message = "최신 컬렉션 top 5  리스트 조회를 성공했을 때 응답"),
            @ApiResponse(code = 401, message = "accessToken 부적합 시 응답")
    })
    @ApiOperation(value = "최신 컬렉션 top 5 리스트 조회 API")
    @GetMapping("/latest-collections5")
    public ResponseEntity<List<MyCollectionResDTO>> getLastestMyCollectionsLimit5 (
            @AuthenticationPrincipal MemberDetails memberDetails
    ){
        List<MyCollection> myCollections = myCollectionService.getLatestMyCollections(memberDetails.getMember().getId());
        List<MyCollectionResDTO> myCollectionResDTOList = myCollections.stream().map(MyCollectionResDTO::new).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(myCollectionResDTOList);
    }
}