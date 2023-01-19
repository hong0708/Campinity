package com.ssafy.campinity.core.service.impl;


import com.ssafy.campinity.core.dto.*;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.campsite.CampsiteScrap;
import com.ssafy.campinity.core.entity.user.User;
import com.ssafy.campinity.core.repository.campsite.CampsiteRepository;
import com.ssafy.campinity.core.repository.campsite.CampsiteScrapRepository;
import com.ssafy.campinity.core.repository.user.UserRepository;
import com.ssafy.campinity.core.repository.campsite.custom.CampsiteCustomRepository;
import com.ssafy.campinity.core.service.CampsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CampsiteServiceImpl implements CampsiteService {

    @Autowired
    CampsiteRepository campsiteRepository;

    @Autowired
    CampsiteCustomRepository campsiteCustomRepository;

    @Autowired
    CampsiteScrapRepository campsiteScrapRepository;

    @Autowired
    UserRepository userRepository;

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
    public List<CampsiteListResDTO> getCampsiteListByFiltering(String keyword, String doName, String sigunguName,
                                                               String[] fclties, String[] amenities, String[] induties,
                                                               String[] themas, String[] allowAnimals, String[] operSeasons, UUID userId) {

        return campsiteCustomRepository.getCampsiteListByFiltering(keyword, doName, sigunguName, fclties, amenities, induties, themas, allowAnimals, operSeasons, userId);
    }

    @Override
    public CampsiteMetaResDTO getCampsiteMetaData(UUID campsiteId) {
        Campsite camp = campsiteRepository.findByUuid(campsiteId).orElseThrow(IllegalAccessError::new);
        return CampsiteMetaResDTO.builder().
                campsiteId(camp.getUuid()).
                campName(camp.getCampName()).
                address(camp.getAddress()).
                firstImageUrl(camp.getFirstImageUrl()).
                build();
    }

    @Override
    public void scrap(UUID userId, UUID campsiteId) {
        Campsite campsite = campsiteRepository.findByUuid(campsiteId).orElseThrow(IllegalArgumentException::new);
        User user = userRepository.findByUuid(userId).orElseThrow(IllegalArgumentException::new);

        CampsiteScrap campsiteScrap = campsiteScrapRepository.findByUserAndCampsite(user, campsite).orElse(null);

        if (campsiteScrap != null) {
            campsiteScrap.changeScrapType();
            campsiteScrapRepository.save(campsiteScrap);
        } else {
            CampsiteScrap savedScrap = campsiteScrapRepository.save(CampsiteScrap.builder().campsite(campsite).user(user).scrapType(true).build());
            campsite.addCampsiteScrap(savedScrap);
            user.addUserScrap(savedScrap);
        }
    }

    @Override
    public CampsiteDetailResDTO getCampsiteDetail(UUID campsiteId, UUID userId) {
        Campsite campsite = campsiteRepository.findByUuid(campsiteId).orElseThrow(IllegalArgumentException::new);
        User user = userRepository.findByUuid(userId).orElseThrow(IllegalArgumentException::new);

        return CampsiteDetailResDTO.builder().camp(campsite).user(user).build();
    }


}
