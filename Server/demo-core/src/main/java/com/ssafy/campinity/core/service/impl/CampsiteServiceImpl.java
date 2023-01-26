package com.ssafy.campinity.core.service.impl;


import com.ssafy.campinity.core.dto.*;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.campsite.CampsiteScrap;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.entity.review.Review;
import com.ssafy.campinity.core.repository.campsite.CampsiteImageRepository;
import com.ssafy.campinity.core.repository.campsite.CampsiteRepository;
import com.ssafy.campinity.core.repository.campsite.CampsiteScrapRepository;
import com.ssafy.campinity.core.repository.campsite.custom.CampsiteCustomRepository;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import com.ssafy.campinity.core.repository.message.MessageRepository;
import com.ssafy.campinity.core.repository.review.ReviewRepository;
import com.ssafy.campinity.core.service.CampsiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CampsiteServiceImpl implements CampsiteService {

    private final CampsiteRepository campsiteRepository;
    private final CampsiteCustomRepository campsiteCustomRepository;
    private final CampsiteScrapRepository campsiteScrapRepository;
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final CampsiteImageRepository campsiteImageRepository;
    private final MessageRepository messageRepository;

    @Override
    @Transactional
    public List<Campsite> getCampsitesByLatLng(LocationInfoDTO locationInfoDTO) {
        Double topLeftLat = locationInfoDTO.getTopLeftLat();
        Double topLeftLng = locationInfoDTO.getTopLeftLng();
        Double bottomRightLat = locationInfoDTO.getBottomRightLat();
        Double bottomRightLng = locationInfoDTO.getBottomRightLng();

        return campsiteRepository.getCampsitesByLatitudeBetweenAndLongitudeBetween(bottomRightLat, topLeftLat,
                topLeftLng, bottomRightLng);
    }

    @Transactional
    @Override
    public List<CampsiteListResDTO> getCampsiteListByFiltering(String keyword, String doName, String sigunguName,
                                                               String[] fclties, String[] amenities, String[] industries,
                                                               String[] themes, String[] allowAnimals, String[] openSeasons,
                                                               int memberId) {

        return campsiteCustomRepository.getCampsiteListByFiltering(keyword, doName, sigunguName, fclties, amenities, industries, themes, allowAnimals, openSeasons, memberId);
    }

    @Transactional
    @Override
    public CampsiteListResDTO getCampsiteMetaData(UUID campsiteId, int memberId) {
        Campsite camp = campsiteRepository.findByUuid(campsiteId).orElseThrow(IllegalAccessError::new);

        List<String> images = campsiteImageRepository.findByCampsite_id(camp.getId()).stream().map(image -> {
            return image.getImagePath();
        }).collect(Collectors.toList());

        Optional<CampsiteScrap> campsiteScrap = campsiteScrapRepository.findByMember_idAndCampsite_id(memberId, camp.getId());

        Boolean isScraped = false;
        if (campsiteScrap.isPresent()) {
            isScraped = true;
        }

        int messageCnt = messageRepository.findByCampsite_idAndExpiredIsFalse(camp.getId()).size();

        return CampsiteListResDTO.builder().camp(camp).images(images).isScraped(isScraped).messageCnt(messageCnt).build();
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
            campsiteScrapRepository.save(CampsiteScrap.builder().campsite(campsite).member(member).build());
            return true;
        }
    }

    @Override
    @Transactional
    public CampsiteDetailResDTO getCampsiteDetail(UUID campsiteId, int memberId) {
        Campsite campsite = campsiteRepository.findByUuid(campsiteId).orElseThrow(IllegalArgumentException::new);
        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId).orElseThrow(IllegalArgumentException::new);

        List<Review> reviews = reviewRepository.findByCampsite_idAndExpiredIsFalse(campsite.getId());

        List<ReviewResDTO> reviewDTOLists = reviews.stream().map(review -> ReviewResDTO.builder().review(review).build()).collect(Collectors.toList());

        List<String> images = campsiteImageRepository.findByCampsite_id(campsite.getId()).stream().map(image -> {
            return image.getImagePath();
        }).collect(Collectors.toList());

        return CampsiteDetailResDTO.builder().camp(campsite).member(member).images(images).reviews(reviewDTOLists).build();
    }
}
