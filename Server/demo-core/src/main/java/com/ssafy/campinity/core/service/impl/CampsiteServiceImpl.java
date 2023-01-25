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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CampsiteServiceImpl implements CampsiteService {

    private final CampsiteRepository campsiteRepository;
    private final CampsiteCustomRepository campsiteCustomRepository;
    private final CampsiteScrapRepository campsiteScrapRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public List<Campsite> getCampsitesByLatLng(LocationInfoDTO locationInfoDTO) {
        Double topLeftLat = locationInfoDTO.getTopLeftLat();
        Double topLeftLng = locationInfoDTO.getTopLeftLng();
        Double bottomRightLat = locationInfoDTO.getBottomRightLat();
        Double bottomRightLng = locationInfoDTO.getBottomRightLng();

        return campsiteRepository.getCampsitesByLatitudeBetweenAndLongitudeBetween(topLeftLat, bottomRightLat,
                topLeftLng, bottomRightLng);
    }

    @Transactional
    @Override
    public List<CampsiteListResDTO> getCampsiteListByFiltering(String keyword, String doName, String sigunguName,
                                                               String[] fclties, String[] amenities, String[] induties,
                                                               String[] themas, String[] allowAnimals, String[] operSeasons, int memberId) {

        return campsiteCustomRepository.getCampsiteListByFiltering(keyword, doName, sigunguName, fclties, amenities, induties, themas, allowAnimals, operSeasons, memberId);
    }

    @Transactional
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
    @Transactional
    public Boolean scrap(int memberId, UUID campsiteId) {
        Campsite campsite = campsiteRepository.findByUuid(campsiteId).orElseThrow(IllegalArgumentException::new);
        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId).orElseThrow(IllegalArgumentException::new);

        Optional<CampsiteScrap> campsiteScrap = campsiteScrapRepository.findByMember_idAndCampsite_id(member.getId(), campsite.getId());

        if (campsiteScrap.isPresent()) {
            campsiteScrapRepository.delete(campsiteScrap.get());
            return false;
        } else {
            campsiteScrapRepository.save(CampsiteScrap.builder().campsite(campsite).member(member).scrapType(true).build());
            return true;
        }
    }

    @Override
    @Transactional
    public CampsiteDetailResDTO getCampsiteDetail(UUID campsiteId, int memberId) {
        Campsite campsite = campsiteRepository.findByUuid(campsiteId).orElseThrow(IllegalArgumentException::new);
        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId).orElseThrow(IllegalArgumentException::new);

        return CampsiteDetailResDTO.builder().camp(campsite).member(member).build();
    }


}
