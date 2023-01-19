package com.ssafy.campinity.api.controller;

import com.ssafy.campinity.api.dto.res.CampsiteLocationInfoDTO;
import com.ssafy.campinity.core.dto.*;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.service.CampsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/campsites")
public class CampsiteController {

    @Autowired
    CampsiteService campsiteService;

    @GetMapping("/scope")
    public ResponseEntity<List<CampsiteLocationInfoDTO>> getCampsiteByScope(
            @RequestParam double topLeftLat,
            @RequestParam double topLeftLng,
            @RequestParam double bottomRightLat,
            @RequestParam double bottomRightLng,
            @RequestParam String doName,
            @RequestParam String siggName) {

        LocationInfoDTO locationInfoDTO = LocationInfoDTO.builder()
                .topLeftLat(topLeftLat)
                .topLeftLng(topLeftLng)
                .bottomRightLat(bottomRightLat)
                .bottomRightLng(bottomRightLng)
                .doName(doName)
                .siggName(siggName)
                .build();

        List<Campsite> result = campsiteService.getCampsitesByLatLng(locationInfoDTO);

        return new ResponseEntity<>(result.stream().map(a -> CampsiteLocationInfoDTO.builder()
                .campsiteId(a.getUuid())
                .lat(a.getLatitude())
                .lng(a.getLongitude()).build()).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/conditions")
    public ResponseEntity<List<CampsiteListResDTO>> getCampsiteListByFiltering(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                                                               @RequestParam(name = "doName", defaultValue = "") String doName,
                                                                               @RequestParam(name = "sigunguName", defaultValue = "") String sigunguName,
                                                                               @RequestParam(name = "fclty", defaultValue = "") String fclty,
                                                                               @RequestParam(name = "amenity", defaultValue = "") String amenity,
                                                                               @RequestParam(name = "industy", defaultValue = "") String induty,
                                                                               @RequestParam(name = "theme", defaultValue = "") String thema,
                                                                               @RequestParam(name = "allowAnimal", defaultValue = "") String allowAnimal,
                                                                               @RequestParam(name = "operSeason", defaultValue = "") String operSeason,
                                                                               @RequestParam(name = "userId") UUID userId) {

        String[] fclties = new String[0];
        if (!fclty.trim().isEmpty()) {
            fclties = fclty.split(" ");
        }

        String[] amenities = new String[0];
        if (!amenity.trim().isEmpty()) {
            amenities = amenity.split(" ");
        }


        String[] induties = new String[0];
        if (!induty.trim().isEmpty()) {
            induties = induty.split(" ");
        }


        String[] themas = new String[0];
        if (!thema.trim().isEmpty()) {
            themas = thema.split(" ");
        }


        String[] operSeasons = new String[0];
        if (!operSeason.trim().isEmpty()) {
            operSeasons = operSeason.split(" ");
        }

        String[] allowAnimals = new String[0];
        if (!allowAnimal.trim().isEmpty()) {
            allowAnimals = allowAnimal.split(" ");
        }

        List<CampsiteListResDTO> result = campsiteService.getCampsiteListByFiltering(keyword.trim(), doName.trim(),
                sigunguName.trim(), fclties, amenities, induties, themas, allowAnimals, operSeasons, userId);

        return new ResponseEntity<List<CampsiteListResDTO>>(result, HttpStatus.OK);
    }

    @GetMapping("/metadata/{campsiteId}")
    public ResponseEntity<CampsiteMetaResDTO> getCampsiteMetaData(@PathVariable("campsiteId") UUID campsiteId){
        return new ResponseEntity<CampsiteMetaResDTO>(campsiteService.getCampsiteMetaData(campsiteId), HttpStatus.OK);
    }

    @GetMapping("/detail/{campsiteId}")
    public ResponseEntity<CampsiteDetailResDTO> getCampsiteDetail(@PathVariable UUID campsiteId, @RequestParam UUID userId) {
        return new ResponseEntity<>(campsiteService.getCampsiteDetail(campsiteId, userId), HttpStatus.OK);
    }

    @PutMapping("/{userId}/scrap/{campsiteId}")
    public ResponseEntity<Object> campsiteScrap(@PathVariable UUID userId,
                                                @PathVariable UUID campsiteId) {
        campsiteService.scrap(userId, campsiteId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
