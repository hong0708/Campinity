package com.ssafy.campinity.api.controller;

import com.ssafy.campinity.api.config.security.jwt.MemberDetails;
import com.ssafy.campinity.core.dto.*;
import com.ssafy.campinity.core.service.CurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/v7/curations")
@RequiredArgsConstructor
public class CurationController {

    private final CurationService curationService;

    @PostMapping("")
    public ResponseEntity<CurationMetaResDTO> createCuration(CurationReqDTO curationReqDTO) {
        CurationMetaResDTO result = curationService.createCuration(curationReqDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v7/curations/" + result.getCurationId());
        return new ResponseEntity<>(result, headers, HttpStatus.CREATED);
    }

    @GetMapping("/banners")
    public ResponseEntity<List<CurationMetaResDTO>> getCurationBannerList() {
        List<CurationMetaResDTO> result = curationService.getCurationBannerList();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/lists")
    public ResponseEntity<List<CurationMetaResDTO>> getCurationList(@RequestParam(defaultValue = "전체") String curationCategory) {
        List<CurationMetaResDTO> result = curationService.getCurationList(curationCategory);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{curationId}")
    public ResponseEntity<CurationDetailResDTO> getCurationDetail(@PathVariable UUID curationId,
                                                                  @AuthenticationPrincipal MemberDetails memberDetails) {
        CurationDetailResDTO result = curationService.getCurationDetail(curationId, memberDetails.getMember().getId());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/scrap-lists")
    public ResponseEntity<List<CurationScrapListResDTO>> getCurationScrapList(@AuthenticationPrincipal MemberDetails memberDetails) {

        List<CurationScrapListResDTO> result = curationService.getCurationScrapList(memberDetails.getMember().getId());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/scraps/{curationId}")
    public ResponseEntity<IsScrapResDTO> scrapCuration(@PathVariable UUID curationId,
                                                       @AuthenticationPrincipal MemberDetails memberDetails) {
        IsScrapResDTO result = curationService.scrapCuration(curationId, memberDetails.getMember().getId());
        return  new ResponseEntity<>(result, HttpStatus.OK);
    }
}
