package com.ssafy.campinity.core.repository.campsite.custom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.campinity.core.entity.campsite.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CampsiteCustomRepository {

    private final EntityManager em;

    public List<Campsite> getCampsiteListByFiltering(String keyword, String doName, String[] sigunguNames,
                                                               String[] fclties, String[] amenities, String[] industries,
                                                               String[] themes, String[] allowAnimals, String[] openSeasons) {

        JPAQueryFactory query = new JPAQueryFactory(em);
        QCampsite qcampsite = new QCampsite("c");

        BooleanBuilder builder = new BooleanBuilder();

        // NPE를 해결하기 위한 기본 조건
        builder.and(qcampsite.uuid.isNotNull());

        // 캠핑장 이름 검색
        if (keyword != null && !keyword.equals("")) {
            builder.and(qcampsite.campName.contains(keyword));
        }

        // 도 검색
        if (doName != null && !doName.equals("")) {
            builder.and(qcampsite.doName.eq(doName));
        }

        // 시군구 검색
        if (sigunguNames != null && sigunguNames.length > 0) {
            builder.and(qcampsite.sigunguName.in(sigunguNames));
        }

        List<Campsite> result = query.select(qcampsite).from(qcampsite).where(builder).fetch();


        // 후처리
        List<Campsite> completedResult = new ArrayList<>();

        boolean[] isInduties = new boolean[5];
        for (String item: industries) {
            isInduties[Integer.parseInt(item)] = true;
        }

        for (Campsite camp: result) {
            // 사업
            boolean[] isIndustriesCampsite = new boolean[5];
            if (0 < industries.length && industries.length < 4) {
                for (CampsiteAndIndustry item: camp.getIndustries()) {
                    isIndustriesCampsite[item.getIndustry().getId()] = true;
                }

                boolean passInduty = false;
                for (int i=1; i < 5; i++) {
                    if (isInduties[i] && isIndustriesCampsite[i]) {
                        passInduty = true;
                        break;
                    }
                }

                if (!passInduty) {
                    continue;
                }
            }

            // 내부시설
            if (0 < fclties.length && fclties.length < 9) {
                boolean passFclty = true;
                if (isInduties[3] && isIndustriesCampsite[3]) {  //  카라반
                    boolean[] isCaravanFcltiesCampsite = new boolean[10];
                    for (CampsiteAndCaravanFclty item: camp.getCaravanFclties()) {
                        isCaravanFcltiesCampsite[item.getCaravanFclty().getId()] = true;
                    }
                    for (String fclty :fclties) {
                        if (!isCaravanFcltiesCampsite[Integer.parseInt(fclty)]) {
                            passFclty = false;
                            break;
                        }
                    }
                }

                if (!passFclty) {
                    continue;
                }

                if (isInduties[4] && isIndustriesCampsite[4]) {  //  글램핑
                    boolean[] isGlampFcltiesCampsite = new boolean[10];
                    for (CampsiteAndGlampFclty item: camp.getGlampFclties()) {
                        isGlampFcltiesCampsite[item.getGlampFclty().getId()] = true;
                    }
                    for (String fclty :fclties) {
                        if (!isGlampFcltiesCampsite[Integer.parseInt(fclty)]) {
                            passFclty = false;
                            break;
                        }
                    }
                }

                if (!passFclty) {
                    continue;
                }
            }

            // themas
            if (0 < themes.length && themes.length < 12) {
                boolean[] isThemasCampsite = new boolean[13];
                for (CampsiteAndTheme item: camp.getThemes()) {
                    isThemasCampsite[item.getTheme().getId()] = true;
                }

                boolean passThemas = false;
                for (String thema: themes) {
                    if (isThemasCampsite[Integer.parseInt(thema)]) {
                        passThemas = true;
                        break;
                    }
                }

                if (!passThemas) {
                    continue;
                }
            }

            // openSeason
            if (0 < openSeasons.length && openSeasons.length < 4) {
                boolean[] isOpenSeasonCampsite = new boolean[5];
                for (CampsiteAndOpenSeason item: camp.getOpenSeasons()) {
                    isOpenSeasonCampsite[item.getOpenSeason().getId()] = true;
                }

                boolean passOpenSeason = false;
                for (String season: openSeasons) {
                    if (isOpenSeasonCampsite[Integer.parseInt(season)]) {
                        passOpenSeason = true;
                        break;
                    }
                }

                if (!passOpenSeason) {
                    continue;
                }
            }

            // amenities
            if (0 < amenities.length && amenities.length < 11) {
                boolean[] isAmenityCampsite = new boolean[12];
                for (CampsiteAndAmenity item: camp.getAmenities()) {
                    isAmenityCampsite[item.getAmenity().getId()] = true;
                }

                boolean passAmenity = true;
                for (String amenity: amenities) {
                    if (!isAmenityCampsite[Integer.parseInt(amenity)]) {
                        passAmenity = false;
                        break;
                    }
                }

                if (!passAmenity) {
                    continue;
                }
            }

            completedResult.add(camp);

//            if (completedResult.size() >= 100) {
//                break;
//            }
        }
        return completedResult;
    }


}
