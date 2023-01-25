package com.ssafy.campinity.api.controller;

import com.ssafy.campinity.api.config.security.jwt.MemberDetails;
import com.ssafy.campinity.core.dto.MyCollectionReqDTO;
import com.ssafy.campinity.core.dto.MyCollectionResDTO;
import com.ssafy.campinity.core.entity.MyCollection.MyCollection;
import com.ssafy.campinity.core.service.MyCollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v5/my-collections")
public class MyCollectionController {

    private final MyCollectionService myCollectionService;

    @PostMapping
    public ResponseEntity<MyCollectionResDTO> createMyCollection(
            @AuthenticationPrincipal MemberDetails memberDetails,
            MyCollectionReqDTO myCollectionReqDTO) {

        MyCollection myCollection = myCollectionService.createMyCollection(myCollectionReqDTO, memberDetails.getMember().getId());

        MyCollectionResDTO myCollectionResDTO = new MyCollectionResDTO(myCollection);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Location", "/api/v5/my-collections/" + myCollection.getUuid().toString());

        return ResponseEntity.status(HttpStatus.CREATED).headers(responseHeaders).body(myCollectionResDTO);
    }

    @PutMapping("/{collectionId}")
    public ResponseEntity<MyCollectionResDTO> editMyCollection(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable String collectionId,
            MyCollectionReqDTO myCollectionReqDTO) throws FileNotFoundException, UnsupportedEncodingException {

        MyCollection myCollection = myCollectionService.editMyCollection(myCollectionReqDTO, collectionId, memberDetails.getMember().getUuid());

        MyCollectionResDTO myCollectionResDTO = new MyCollectionResDTO(myCollection);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Location", "/api/v5/my-collections/" + myCollection.getUuid().toString());

        return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(myCollectionResDTO);
    }

    @GetMapping
    public ResponseEntity<List<MyCollectionResDTO>> getMyCollections(
            @AuthenticationPrincipal MemberDetails memberDetails
    ){
        List<MyCollection> myCollections = myCollectionService.getMyCollections(memberDetails.getMember().getId());
        List<MyCollectionResDTO> myCollectionResDTOList = myCollections.stream().map(MyCollectionResDTO::new).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(myCollectionResDTOList);
    }

    @GetMapping("/{collectionId}")
    public ResponseEntity<Object> getMyCollection(@PathVariable String collectionId){

        MyCollection myCollection = myCollectionService.getMyCollection(collectionId);
        MyCollectionResDTO myCollectionResDTO = new MyCollectionResDTO(myCollection);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Location", "/api/v5/my-collections/" + myCollection.getUuid().toString());

        return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(myCollectionResDTO);

    }

    @DeleteMapping("/{collectionId}")
    public ResponseEntity<Object> deleteCollection(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable String collectionId) throws FileNotFoundException {

        myCollectionService.deleteMyCollection(collectionId, memberDetails.getMember().getUuid());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

    }
}
