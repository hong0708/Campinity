package com.ssafy.campinity.core.service.impl;


import com.ssafy.campinity.core.dto.*;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.campsite.CampsiteScrap;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.repository.campsite.CampsiteRepository;
import com.ssafy.campinity.core.repository.campsite.CampsiteScrapRepository;
import com.ssafy.campinity.core.repository.campsite.custom.CampsiteCustomRepository;
import com.ssafy.campinity.core.repository.member.MemberRepository;
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
    MemberRepository memberRepository;

    @Override
    public List<Campsite> getCampsitesByLatLng(LocationInfoDTO locationInfoDTO) {
        Double topLeftLat = locationInfoDTO.getTopLeftLat();
        Double topLeftLng = locationInfoDTO.getTopLeftLng();
        Double bottomRightLat = locationInfoDTO.getBottomRightLat();
        Double bottomRightLng = locationInfoDTO.getBottomRightLng();

        return campsiteRepository.getCampsitesByLatitudeBetweenAndLongitudeBetween(topLeftLat, bottomRightLat,
                topLeftLng, bottomRightLng);
    }



    @Override
    public List<CampsiteListResDTO> getCampsiteListByFiltering(String keyword, String doName, String sigunguName,
                                                               String[] fclties, String[] amenities, String[] induties,
                                                               String[] themas, String[] allowAnimals, String[] operSeasons, UUID memberId) {

        return campsiteCustomRepository.getCampsiteListByFiltering(keyword, doName, sigunguName, fclties, amenities, induties, themas, allowAnimals, operSeasons, memberId);
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
    public void scrap(UUID memberId, UUID campsiteId) {
        Campsite campsite = campsiteRepository.findByUuid(campsiteId).orElseThrow(IllegalArgumentException::new);
        Member member = memberRepository.findMemberByUuidAndExpiredIsFalse(memberId).orElseThrow(IllegalArgumentException::new);

        CampsiteScrap campsiteScrap = campsiteScrapRepository.findByMemberAndCampsite(member, campsite).orElse(null);

        if (campsiteScrap != null) {
            campsiteScrap.changeScrapType();
            campsiteScrapRepository.save(campsiteScrap);
        } else {
            CampsiteScrap savedScrap = campsiteScrapRepository.save(CampsiteScrap.builder().campsite(campsite).member(member).scrapType(true).build());
            campsite.addCampsiteScrap(savedScrap);
            member.addUserScrap(savedScrap);
        }
    }

    @Override
    public CampsiteDetailResDTO getCampsiteDetail(UUID campsiteId, UUID memberId) {
        Campsite campsite = campsiteRepository.findByUuid(campsiteId).orElseThrow(IllegalArgumentException::new);
        Member member = memberRepository.findMemberByUuidAndExpiredIsFalse(memberId).orElseThrow(IllegalArgumentException::new);

        return CampsiteDetailResDTO.builder().camp(campsite).member(member).build();
    }


}
