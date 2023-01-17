package com.ssafy.campinity.demo.batch.service;


import com.ssafy.campinity.core.entity.campsite.*;
import com.ssafy.campinity.core.repository.campsite.*;
import com.ssafy.campinity.demo.batch.dto.gocamp.res.ResCampsiteDto;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
@SpringBootTest
public class GetGocampApiJsonTest {

    @Autowired
    CrawlGocampApi crawlGocampApi;

    @Autowired
    AmenityRepository amenityRepository;

    @Autowired
    CaravanFcltyRepository caravanFcltyRepository;

    @Autowired
    GlampFcltyRepository glampFcltyRepository;

    @Autowired
    IndustryRepository industryRepository;

    @Autowired
    OpenSeasonRepository openSeasonRepository;

    @Autowired
    ThemeRepository themeRepository;

    @Autowired
    CampsiteRepository campsiteRepository;

    @Autowired
    CampsiteAndAmenityRepository campsiteAndAmenityRepository;

    @Autowired
    CampsiteAndCaravanFcltyRepository campsiteAndCaravanFcltyRepository;

    @Autowired
    CampsiteAndGlampFcltyRepository campsiteAndGlampFcltyRepository;
    @Autowired
    CampsiteAndIndustryRepository campsiteAndIndustryRepository;

    @Autowired
    CampsiteAndOpenSeasonRepository campsiteAndOpenSeasonRepository;
    @Autowired
    CampsiteAndThemeRepository campsiteAndThemeRepository;


    @Test
    @DisplayName("캠핑 사이트 관계 테이블 아이템 추출")
    void getGocampFilterItemTest() throws IOException {

        int numOfRows = 3329;
        HashSet<String> amenityItems = new HashSet<>();
        HashSet<String> caravanItems = new HashSet<>();
        HashSet<String> glampItems = new HashSet<>();
        HashSet<String> industryItems = new HashSet<>();
        HashSet<String> openSeasonItems = new HashSet<>();
        HashSet<String> themeItems = new HashSet<>();

        List<ResCampsiteDto> campsiteList = crawlGocampApi.getCampsiteList(numOfRows);
        for (ResCampsiteDto res : campsiteList){
            if (res.getContentId() == 100596) {
                continue;
            }
            String operDay = res.getOperDeCl().replace("+", ", ");
            Campsite campsite = Campsite.builder()
                    .uuid(UUID.randomUUID())
                    .contentId(res.getContentId())
                    .campName(res.getFacltNm())
                    .firstImageUrl(res.getFirstImageUrl())
                    .address(res.getAddr1())
                    .doName(res.getDoNm())
                    .sigunguName(res.getSigunguNm())
                    .longitude(Double.valueOf(res.getMapX()))
                    .latitude(Double.valueOf(res.getMapY()))
                    .phoneNumber(res.getTel())
                    .homepage(res.getHomepage())
                    .reserveType(res.getResveCl())
                    .intro(res.getIntro())
                    .lineIntro(res.getLineIntro())
                    .experienceProgram(res.getExprnProgrm())
                    .subFacilityEtc(res.getSbrsEtc())
                    .dayOperation(operDay)
                    .allowAnimal(res.getAnimalCmgCl())
                    .build();

            campsiteRepository.save(campsite);
        }
        campsiteList.forEach(campsite -> amenityItems.addAll(new HashSet<>(Lists.newArrayList(campsite.getSbrsCl().split(",")))));
        campsiteList.forEach(campsite -> caravanItems.addAll(new HashSet<>(Lists.newArrayList(campsite.getCaravInnerFclty().split(",")))));
        campsiteList.forEach(campsite -> glampItems.addAll(new HashSet<>(Lists.newArrayList(campsite.getGlampInnerFclty().split(",")))));
        campsiteList.forEach(campsite -> industryItems.addAll(new HashSet<>(Lists.newArrayList(campsite.getInduty().split(",")))));
        campsiteList.forEach(campsite -> openSeasonItems.addAll(new HashSet<>(Lists.newArrayList(campsite.getOperPdCl().split(",")))));
        campsiteList.forEach(campsite -> themeItems.addAll(new HashSet<>(Lists.newArrayList(campsite.getThemaEnvrnCl().split(",")))));

        amenityItems.remove("");
        caravanItems.remove("");
        glampItems.remove("");
        industryItems.remove("");
        openSeasonItems.remove("");
        themeItems.remove("");

        List amenityList = new ArrayList(amenityItems);
        List caravanList = new ArrayList(caravanItems);
        List glampList = new ArrayList(glampItems);
        List industryList = new ArrayList(industryItems);
        List openSeasonList = new ArrayList(openSeasonItems);
        List themeList = new ArrayList(themeItems);

        List<Amenity> amenities = new ArrayList<>();
        List<CaravanFclty> caravanFclties = new ArrayList<>();
        List<GlampFclty> glampFclties = new ArrayList<>();
        List<Industry> industries = new ArrayList<>();
        List<OpenSeason> openSeasons = new ArrayList<>();
        List<Theme> themes = new ArrayList<>();

        for (Object s : amenityList){
            Amenity amenity = new Amenity();
            amenity.setAmenityName(String.valueOf(s));
            amenities.add(amenityRepository.save(amenity));
        }
        for (Object s : caravanList){
            CaravanFclty caravanFclty = new CaravanFclty();
            caravanFclty.setFcltyName(String.valueOf(s));
            caravanFclties.add(caravanFcltyRepository.save(caravanFclty));
        }
        for (Object s : glampList){
            GlampFclty glampFclty = new GlampFclty();
            glampFclty.setFcltyName(String.valueOf(s));
            glampFclties.add(glampFcltyRepository.save(glampFclty));
        }
        for (Object s : industryList){
            Industry industry = new Industry();
            industry.setIndustryName(String.valueOf(s));
            industries.add(industryRepository.save(industry));
        }
        for (Object s : openSeasonList){
            OpenSeason openSeason = new OpenSeason();
            openSeason.setSeasonName(String.valueOf(s));
            openSeasons.add(openSeasonRepository.save(openSeason));
        }
        for (Object s : themeList) {
            Theme theme = new Theme();
            theme.setThemeName(String.valueOf(s));
            themes.add(themeRepository.save(theme));
        }

        List<ResCampsiteDto> campsiteList1 = crawlGocampApi.getCampsiteList(numOfRows);

        for (ResCampsiteDto res : campsiteList1){
            Campsite camp = campsiteRepository.findByContentId(res.getContentId());

            List<String> amenityItems_ = Lists.newArrayList(res.getSbrsCl().split(","));
            List<String> caravanItems_ = Lists.newArrayList(res.getCaravInnerFclty().split(","));
            List<String> glampItems_ = Lists.newArrayList(res.getGlampInnerFclty().split(","));
            List<String> industryItems_ = Lists.newArrayList(res.getInduty().split(","));
            List<String> openSeasonItems_ = Lists.newArrayList(res.getOperPdCl().split(","));
            List<String> themeItems_ = Lists.newArrayList(res.getThemaEnvrnCl().split(","));;


            amenityItems_.remove("");
            caravanItems_.remove("");
            glampItems_.remove("");
            industryItems_.remove("");
            openSeasonItems_.remove("");
            themeItems_.remove("");

            if (openSeasonItems_.size() == 0){
                openSeasonItems_ = Lists.newArrayList("봄", "여름", "가을", "겨울");
            }

            for (String item : amenityItems_){

                Amenity amenity = amenityRepository.findByAmenityName(item);
                CampsiteAndAmenity campsiteAndAmenity = CampsiteAndAmenity.builder().campsite(camp).amenity(amenity).build();
                campsiteAndAmenityRepository.save(campsiteAndAmenity);

            }
            for (String item : caravanItems_){
                CaravanFclty caravanFclty = caravanFcltyRepository.findByFcltyName(item);
                CampsiteAndCaravanFclty campsiteAndCaravanFclty = new CampsiteAndCaravanFclty(camp, caravanFclty);
                campsiteAndCaravanFcltyRepository.save(campsiteAndCaravanFclty);
            }

            for (String item : glampItems_){
                GlampFclty glampFclty = glampFcltyRepository.findByFcltyName(item);
                CampsiteAndGlampFclty campsiteAndGlampFclty = new CampsiteAndGlampFclty(camp, glampFclty);
                campsiteAndGlampFcltyRepository.save(campsiteAndGlampFclty);
            }

            for (String item : industryItems_){
                Industry industry = industryRepository.findByIndustryName(item);
                CampsiteAndIndustry campsiteAndIndustry = new CampsiteAndIndustry(camp, industry);
                campsiteAndIndustryRepository.save(campsiteAndIndustry);
            }

            for (String item : openSeasonItems_){
                OpenSeason openSeason = openSeasonRepository.findBySeasonName(item);
                CampsiteAndOpenSeason campsiteAndOpenSeason = new CampsiteAndOpenSeason(camp, openSeason);
                campsiteAndOpenSeasonRepository.save(campsiteAndOpenSeason);
            }

            for (String item : themeItems_){
                Theme theme = themeRepository.findByThemeName(item);
                CampsiteAndTheme campsiteAndTheme = new CampsiteAndTheme(camp, theme);
                campsiteAndThemeRepository.save(campsiteAndTheme);
            }

        }

    }

}
