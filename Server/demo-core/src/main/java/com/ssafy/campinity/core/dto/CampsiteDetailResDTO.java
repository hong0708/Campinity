package com.ssafy.campinity.core.dto;

import com.ssafy.campinity.core.entity.campsite.*;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.entity.review.Review;
import io.swagger.models.auth.In;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CampsiteDetailResDTO {

    private String campsiteId;

    private List<Integer> amenities;

    private List<Integer> caravanfclties;

    private List<Integer> glampfclties;

    private List<String> openSeasons;

    private List<Integer> themes;

    private List<String> industries;

    private Boolean isScraped;

    private String campName;

    private String address;

    private String phoneNumber;

    private String homepage;

    private String reserveType;

    private String intro;

    private String lineIntro;

    private String experienceProgram;

    private String subFacilityEtc;

    private String dayOperation;

    private String allowAnimal;

    private List<String> images;
    private List<ReviewResDTO> reviews;
    private double total_rate;

    @Builder
    public CampsiteDetailResDTO(Campsite camp, Member member, List<ReviewResDTO> reviews, List<String> images) {
        boolean isScraped = false;
        for (CampsiteScrap cs: camp.getScraps()) {
            if (cs.getMember().equals(member)){
                isScraped = true;
                break;
            }
        }

        List<Integer> amenities = new ArrayList<>();
        for (CampsiteAndAmenity item: camp.getAmenities()) {
            amenities.add(item.getAmenity().getId());
        }

        List<Integer> caravanfclties = new ArrayList<>();
        for (CampsiteAndCaravanFclty item: camp.getCaravanFclties()) {
            caravanfclties.add(item.getCaravanFclty().getId());
        }

        List<Integer> glampfclties = new ArrayList<>();
        for (CampsiteAndGlampFclty item: camp.getGlampFclties()) {
            glampfclties.add(item.getGlampFclty().getId());
        }

        List<String> openSeasons = new ArrayList<>();
        for (CampsiteAndOpenSeason item: camp.getOpenSeasons()) {
            openSeasons.add(item.getOpenSeason().getSeasonName());
        }

        List<Integer> themes = new ArrayList<>();
        for (CampsiteAndTheme item: camp.getThemes()) {
            themes.add(item.getTheme().getId());
        }

        List<String> industies = new ArrayList<>();
        for (CampsiteAndIndustry item: camp.getIndustries()) {
            industies.add(item.getIndustry().getIndustryName());
        }

        double total_rate = 0.0;
        for (ReviewResDTO item: reviews) {
            total_rate += item.getRate();
        }
        if (!reviews.isEmpty()) {
            total_rate /= reviews.size();
        }

        this.campsiteId = camp.getUuid().toString();
        this.amenities = amenities;
        this.caravanfclties = caravanfclties;
        this.glampfclties = glampfclties;
        this.openSeasons = openSeasons;
        this.themes = themes;
        this.industries = industies;
        this.isScraped = isScraped;
        this.campName = camp.getCampName();
        this.address = camp.getAddress();
        this.phoneNumber = camp.getPhoneNumber();
        this.homepage = camp.getHomepage();
        this.reserveType = camp.getReserveType();
        this.intro = camp.getIntro();
        this.lineIntro = camp.getLineIntro();
        this.experienceProgram = camp.getExperienceProgram();
        this.subFacilityEtc = camp.getSubFacilityEtc();
        this.dayOperation = camp.getDayOperation();
        this.allowAnimal = camp.getAllowAnimal();
        this.reviews = reviews;
        this.total_rate = total_rate;
        this.images = images;
    }
}
