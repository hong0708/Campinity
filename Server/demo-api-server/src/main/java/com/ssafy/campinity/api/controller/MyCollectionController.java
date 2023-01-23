package com.ssafy.campinity.api.controller;

import com.ssafy.campinity.core.dto.MyCollectionReqDTO;
import com.ssafy.campinity.core.dto.MyCollectionResDTO;
import com.ssafy.campinity.core.entity.MyCollection.MyCollection;
import com.ssafy.campinity.core.service.MyCollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final String userUuid = "ae7766ef-a63c-4be3-ae7b-352112813328"; // 임시 테스트용 member uuid


    @PostMapping
    public ResponseEntity<MyCollectionResDTO> createMyCollection(MyCollectionReqDTO myCollectionReqDTO) {

        try {
            MyCollection myCollection = myCollectionService.createMyCollection(myCollectionReqDTO, userUuid);

            MyCollectionResDTO myCollectionResDTO = new MyCollectionResDTO(myCollection);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Location", "/api/v5/my-collections/" + myCollection.getUuid().toString());

            return ResponseEntity.status(HttpStatus.CREATED).headers(responseHeaders).body(myCollectionResDTO);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{collectionId}")
    public ResponseEntity<MyCollectionResDTO> editMyCollection(
            @PathVariable String collectionId,
            MyCollectionReqDTO myCollectionReqDTO) {

        try {
            MyCollection myCollection = myCollectionService.editMyCollection(myCollectionReqDTO, collectionId);

            MyCollectionResDTO myCollectionResDTO = new MyCollectionResDTO(myCollection);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Location", "/api/v5/my-collections/" + myCollection.getUuid().toString());

            return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(myCollectionResDTO);
        }
        catch (IllegalArgumentException e) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);}
        catch (FileNotFoundException | UnsupportedEncodingException e) { return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); }
    }

    @GetMapping
    public ResponseEntity<List<MyCollectionResDTO>> getMyCollections(){
        try {
            List<MyCollection> myCollections = myCollectionService.getMyCollections(userUuid);
            List<MyCollectionResDTO> myCollectionResDTOList = myCollections.stream().map(MyCollectionResDTO::new).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(myCollectionResDTOList);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{collectionId}")
    public ResponseEntity<Object> getMyCollection(@PathVariable String collectionId){
        try {
            MyCollection myCollection = myCollectionService.getMyCollection(collectionId);
            MyCollectionResDTO myCollectionResDTO = new MyCollectionResDTO(myCollection);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Location", "/api/v5/my-collections/" + myCollection.getUuid().toString());

            return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(myCollectionResDTO);
        }
        catch (IllegalArgumentException e){ return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);}
    }

    @DeleteMapping("/{collectionId}")
    public ResponseEntity<Object> deleteCollection(@PathVariable String collectionId) {
        try {
            myCollectionService.deleteMyCollection(collectionId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (FileNotFoundException e) { return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); }
    }
}
