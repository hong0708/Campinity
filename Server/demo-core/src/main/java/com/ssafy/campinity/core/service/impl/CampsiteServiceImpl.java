package com.ssafy.campinity.core.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.ssafy.campinity.core.repository.redis.RedisDao;
import com.ssafy.campinity.core.repository.review.ReviewRepository;
import com.ssafy.campinity.core.service.CampsiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private final RedisDao redisDao;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public List<Campsite> getMetaDataListByLatLng(LocationInfoDTO locationInfoDTO) {
        Double topLeftLat = locationInfoDTO.getTopLeftLat();
        Double topLeftLng = locationInfoDTO.getTopLeftLng();
        Double bottomRightLat = locationInfoDTO.getBottomRightLat();
        Double bottomRightLng = locationInfoDTO.getBottomRightLng();

        return campsiteRepository.getCampsitesByLatitudeBetweenAndLongitudeBetween(bottomRightLat, topLeftLat,
                topLeftLng, bottomRightLng);
    }

    @Override
    @Transactional
    public List<CampsiteListResDTO> getCampsitesByLatLng(LocationInfoDTO locationInfoDTO, int memberId) {
        Double topLeftLat = locationInfoDTO.getTopLeftLat();
        Double topLeftLng = locationInfoDTO.getTopLeftLng();
        Double bottomRightLat = locationInfoDTO.getBottomRightLat();
        Double bottomRightLng = locationInfoDTO.getBottomRightLng();

        List<Campsite> campsites = campsiteRepository.getCampsitesByLatitudeBetweenAndLongitudeBetween(bottomRightLat,
                topLeftLat, topLeftLng, bottomRightLng);

        List<CampsiteListResDTO> results = new ArrayList<>();
        for (Campsite camp: campsites) {

            List<String> images = camp.getImages().stream().map(image -> {
                return image.getImagePath();
            }).collect(Collectors.toList());

            boolean isScraped = camp.getScraps().stream().anyMatch(campsiteScrap -> campsiteScrap.getId() == memberId);

            int messageCnt = camp.getMessages().size();

            results.add(CampsiteListResDTO.builder().camp(camp).isScraped(isScraped).messageCnt(messageCnt).images(images).build());
        }
        return results;
    }

    @Transactional
    @Override
    public List<CampsiteListResDTO> getCampsiteListByFiltering(String keyword, String doName, String[] sigunguNames,
                                                               String[] fclties, String[] amenities, String[] industries,
                                                               String[] themes, String[] allowAnimals, String[] openSeasons,
                                                               int memberId) {

        List<Campsite> campsites = campsiteCustomRepository.getCampsiteListByFiltering(keyword, doName, sigunguNames,
                fclties, amenities, industries, themes, allowAnimals, openSeasons);

        List<CampsiteListResDTO> results = campsites.stream().map(camp -> {
            boolean isScraped = camp.getScraps().stream().anyMatch(campsiteScrap -> campsiteScrap.getId() == memberId);
            int messageCnt = camp.getMessages().size();

            List<String> images = camp.getImages().stream().map(image -> {
                return image.getImagePath();
            }).collect(Collectors.toList());

            return CampsiteListResDTO.builder().camp(camp).isScraped(isScraped).messageCnt(messageCnt).images(images).build();

//            Optional<CampsiteScrap> campsiteScrap = campsiteScrapRepository.findByMember_idAndCampsite_id(memberId, camp.getId());
//
//            Boolean isScraped = false;
//            if (campsiteScrap.isPresent()) {
//                isScraped = true;
//            }
//
//            int messageCnt = messageRepository.findByCampsite_idAndExpiredIsFalse(camp.getId()).size();
//
//            List<String> images = campsiteImageRepository.findByCampsite_id(camp.getId()).stream().map(image -> {
//                return image.getImagePath();
//            }).collect(Collectors.toList());
//
//            return CampsiteListResDTO.builder().camp(camp).isScraped(isScraped).images(images).messageCnt(messageCnt).build();
        }).collect(Collectors.toList());

        return results;
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

        List<Review> reviews = reviewRepository.findByCampsite_idAndExpiredIsFalseOrderByCreatedAtDesc(campsite.getId());

        List<ReviewResDTO> reviewDTOLists = reviews.stream().map(review -> ReviewResDTO.builder().review(review).build()).collect(Collectors.toList());

        List<String> images = campsiteImageRepository.findByCampsite_id(campsite.getId()).stream().map(image -> {
            return image.getImagePath();
        }).collect(Collectors.toList());

        return CampsiteDetailResDTO.builder().camp(campsite).member(member).images(images).reviews(reviewDTOLists).build();
    }

    @Override
    public List<CampsiteMetaResDTO> getCampsiteScrapList(int memberId) {
        List<Campsite> campsites = campsiteRepository.findCampsiteScrapList(memberId);
        return campsites.stream().map(campsite -> {
            return CampsiteMetaResDTO.builder().campsite(campsite).isScraped(true).build();
        }).collect(Collectors.toList());
    }

    @Override
    public List<Campsite> getCampsiteByCampName(String keyword) {
        return campsiteRepository.findByCampNameContains(keyword);
    }

    @Override
    public List<CampsiteRankingResDTO> getHottestCampsite() throws JsonProcessingException {
        String highestCampsite = redisDao.getValues("hottestCampsite");
        return objectMapper.readValue(highestCampsite, new TypeReference<List<CampsiteRankingResDTO>>(){});
    }

    @Override
    public List<CampsiteRankingResDTO> getHighestCampsite() throws JsonProcessingException {
        String highestCampsite = redisDao.getValues("highestCampsite");
        return objectMapper.readValue(highestCampsite, new TypeReference<List<CampsiteRankingResDTO>>(){});
    }
}
