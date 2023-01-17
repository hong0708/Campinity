package com.ssafy.campinity.api.controller;

import com.ssafy.campinity.api.dto.res.CampsiteLocationInfoDTO;
import com.ssafy.campinity.core.dto.CampsiteListDTO;
import com.ssafy.campinity.core.dto.LocationInfoDTO;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.service.CampsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
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

    @GetMapping("/lists")
    public ResponseEntity<List<CampsiteListDTO>> getCampsiteListByFiltering(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                                                            @RequestParam(name = "do-name", defaultValue = "") String doName,
                                                                            @RequestParam(name = "sigungu-name", defaultValue = "") String sigunguName,
                                                                            @RequestParam(name = "fclty", defaultValue = "") String fclty,
                                                                            @RequestParam(name = "amenity", defaultValue = "") String amenity,
                                                                            @RequestParam(name = "induty", defaultValue = "") String induty,
                                                                            @RequestParam(name = "theme", defaultValue = "") String thema,
                                                                            @RequestParam(name = "allow-animal", defaultValue = "") String allowAnimal,
                                                                            @RequestParam(name = "oper-season", defaultValue = "") String operSeason) {

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


        List<CampsiteListDTO> result = campsiteService.getCampsiteListByFiltering(keyword.trim(), doName.trim(),
                sigunguName.trim(), fclties, amenities, induties, themas, allowAnimals, operSeasons);

        return new ResponseEntity<List<CampsiteListDTO>>(result, HttpStatus.OK);
    }
}
