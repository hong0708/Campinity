package com.ssafy.campinity.core.repository.campsite.custom;

import com.ssafy.campinity.core.dto.CampsiteListResDTO;
import com.ssafy.campinity.core.entity.campsite.*;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.repository.campsite.CampsiteImageRepository;
import com.ssafy.campinity.core.repository.campsite.CampsiteScrapRepository;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import com.ssafy.campinity.core.repository.message.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CampsiteCustomRepository {

    private final EntityManager em;
    private final MemberRepository memberRepository;
    private final CampsiteScrapRepository campsiteScrapRepository;
    private final MessageRepository messageRepository;
    private final CampsiteImageRepository campsiteImageRepository;


    public List<CampsiteListResDTO> getCampsiteListByFiltering(String keyword, String doName, String[] sigunguNames,
                                                               String[] fclties, String[] amenities, String[] industries,
                                                               String[] themes, String[] allowAnimals, String[] openSeasons, int requestMemberId) {
        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(requestMemberId).orElseThrow(IllegalArgumentException::new);
        String query = "Select c From Campsite c ";

        // join절
        String joinClause = "";
        String tableName = "";
        String nickname = "";

        // 부대시설
        if (0 < amenities.length && amenities.length < 11) {
            tableName = "CampsiteAndAmenity";
            for (int i = 0; i < amenities.length; i++) {
                nickname = "ca" + Integer.toString(i);
                joinClause = joinClause + " JOIN " + tableName + " AS " + nickname + " ON " + nickname + ".amenity.id = " + amenities[i] +
                        " AND " + "c.id = " + nickname + ".campsite.id ";
            }
        }

        // where절
        String whereClause = "";
        boolean before = false;
        if (keyword != null && !keyword.trim().isEmpty()) { // 검색어
            whereClause += " c.campName LIKE '%" + keyword +"%'";
            before = true;
        }

        if (doName != null && !doName.trim().isEmpty()) {  // 도
            if (before) {
                whereClause += " And";
            }
            whereClause += " c.doName = '" + doName + "'";
            before = true;
        }

        if (sigunguNames.length > 0) { // 시군구
            if (before) {
                whereClause += " And";
            }
            whereClause += " c.sigunguName IN (";
            for (int i = 0; i < sigunguNames.length; i++) {
                whereClause = whereClause + "'" + sigunguNames[i] + "'";
                if (i < sigunguNames.length - 1) {
                    whereClause = whereClause + " , ";
                }
            }
            whereClause += ")";
            before = true;
        }

        if (allowAnimals.length > 0) { // 반려동물 가능 여부(수정)
            if (before) {
                whereClause += " And ";
            }
            whereClause = whereClause + "(";
            for (int i = 0; i < allowAnimals.length; i++) {
                whereClause = whereClause + "c.allowAnimal = '" + allowAnimals[i] + "'";
                if (i < (allowAnimals.length - 1)) {
                    whereClause = whereClause + " OR ";
                }
            }
            whereClause += ")";
            before = true;
        }

        if (before) {
            whereClause = " Where " + whereClause;
        }

        query = query + joinClause + whereClause;

        List<Campsite> result = em.createQuery(query, Campsite.class).getResultList();


        // 후처리
        List<CampsiteListResDTO> completedResult = new ArrayList<>();

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

            Optional<CampsiteScrap> campsiteScrap = campsiteScrapRepository.findByMember_idAndCampsite_id(member.getId(), camp.getId());

            Boolean isScraped = false;
            if (campsiteScrap.isPresent()) {
                isScraped = true;
            }

            int messageCnt = messageRepository.findByCampsite_idAndExpiredIsFalse(camp.getId()).size();

            List<String> images = campsiteImageRepository.findByCampsite_id(camp.getId()).stream().map(image -> {
                return image.getImagePath();
            }).collect(Collectors.toList());

            completedResult.add(CampsiteListResDTO.builder().camp(camp).isScraped(isScraped).images(images).messageCnt(messageCnt).build());
        }
        return completedResult;
    }
}
