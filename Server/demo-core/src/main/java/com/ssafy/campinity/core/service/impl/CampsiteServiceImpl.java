package com.ssafy.campinity.core.service.impl;


import com.ssafy.campinity.core.dto.CampsiteListDTO;
import com.ssafy.campinity.core.dto.LocationInfoDTO;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.repository.campsite.CampsiteRepository;
import com.ssafy.campinity.core.repository.campsite.custom.CampsiteCustomRepository;
import com.ssafy.campinity.core.service.CampsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampsiteServiceImpl implements CampsiteService {

    @Autowired
    CampsiteRepository campsiteRepository;

    @Autowired
    CampsiteCustomRepository campsiteCustomRepository;

    @Override
    public List<Campsite> getCampsitesByLatLng(LocationInfoDTO locationInfoDTO) {
        Double topLeftLat = locationInfoDTO.getTopLeftLat();
        Double topLeftLng = locationInfoDTO.getTopLeftLng();
        Double bottomRightLat = locationInfoDTO.getBottomRightLat();
        Double bottomRightLng = locationInfoDTO.getBottomRightLng();

        return campsiteRepository.getCampsitesByLatitudeBetweenAndLongitudeBetween(topLeftLat, topLeftLng,
                bottomRightLat, bottomRightLng);
    }



    @Override
    public List<CampsiteListDTO> getCampsiteListByFiltering(String keyword, String doName, String sigunguName,
                                                             String[] fclties, String[] amenities, String[] induties,
                                                             String[] themas, String[] allowAnimals, String[] operSeasons) {

        return campsiteCustomRepository.getCampsiteListByFiltering(keyword, doName, sigunguName, fclties, amenities, induties, themas, allowAnimals, operSeasons);
    }


}
