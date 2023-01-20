package com.ssafy.campinity.core.entity.campsite;

import com.ssafy.campinity.core.dto.CampsiteListResDTO;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.repository.campsite.*;
import com.ssafy.campinity.core.repository.campsite.custom.CampsiteCustomRepository;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class CampsiteGetListByFiltringTest {

    @Autowired
    CampsiteCustomRepository campsiteCustomRepository;

    @Autowired
    CampsiteRepository campsiteRepository;

    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("캠핑장 필터링 조회 where절만 test")
    public void CampsiteGetListByFilteringWhereClause() {
        String[] allowAnimalList1 = {"가능", "가능(소형견)"};

        Member member = Member.builder().name("test user").uuid(UUID.randomUUID()).build();

        Member savedMember = memberRepository.save(member);

        List<CampsiteListResDTO> result1 = campsiteCustomRepository.getCampsiteListByFiltering(
                "캠핑", "충청북도", "단양군", new String[0], new String[0],
                new String[0], new String[0], allowAnimalList1, new String[0], savedMember.getUuid());

        for (CampsiteListResDTO campDto: result1) {
            Campsite camp = campsiteRepository.findByUuid(UUID.fromString(campDto.getCampsiteId())).orElseThrow(IllegalArgumentException::new);
            assertThat(camp.getCampName()).contains("캠핑");
            assertThat(camp.getDoName()).isEqualTo("충청북도");
            assertThat(camp.getSigunguName()).isEqualTo("단양군");
            assertThat(camp.getAllowAnimal()).isIn("가능", "가능(소형견)");
        }

        String[] allowAnimalList2 = {"가능", "가능(소형견)", "불가능"};
        List<CampsiteListResDTO> result2 = campsiteCustomRepository.getCampsiteListByFiltering(
                "오토", "강원도", "삼척시", new String[0], new String[0],
                new String[0], new String[0], allowAnimalList2, new String[0], savedMember.getUuid());

        for (CampsiteListResDTO campDto: result2) {
            Campsite camp = campsiteRepository.findByUuid(UUID.fromString(campDto.getCampsiteId())).orElseThrow(IllegalArgumentException::new);
            assertThat(camp.getCampName()).contains("오토");
            assertThat(camp.getDoName()).isEqualTo("강원도");
            assertThat(camp.getSigunguName()).isEqualTo("삼척시");
            assertThat(camp.getAllowAnimal()).isIn("가능", "가능(소형견)", "불가능");
        }
    }


    @Test
    @DisplayName("캠핑장 필터링 조회 부대시설 join test")
    public void CampsiteGetListByFilteringJoinAmenity() {
        String[] amenities_list1 = {"1", "2"};

        Member member = Member.builder().name("test user").uuid(UUID.randomUUID()).build();

        Member savedMember = memberRepository.save(member);

        List<CampsiteListResDTO> result1 = campsiteCustomRepository.getCampsiteListByFiltering(
                "", "", "", new String[0], amenities_list1,
                new String[0], new String[0], new String[0], new String[0], savedMember.getUuid());

        for(CampsiteListResDTO campDto: result1) {
            Campsite camp = campsiteRepository.findByUuid(UUID.fromString(campDto.getCampsiteId())).orElseThrow(IllegalArgumentException::new);
            boolean flag1 = false;
            boolean flag2 = false;
            for (CampsiteAndAmenity ca: camp.getAmenities()) {
                if (ca.getAmenity().getId() == 1) {
                    flag1 = true;
                }
                if (ca.getAmenity().getId() == 2) {
                    flag2 = true;
                }
            }
            assertThat(flag1 && flag2).isEqualTo(true);
        }

        String[] amenities_list2 = {"3", "4"};

        List<CampsiteListResDTO> result2 = campsiteCustomRepository.getCampsiteListByFiltering(
                "", "", "", new String[0], amenities_list2,
                new String[0], new String[0], new String[0], new String[0], savedMember.getUuid());

        for(CampsiteListResDTO campDto: result2) {
            Campsite camp = campsiteRepository.findByUuid(UUID.fromString(campDto.getCampsiteId())).orElseThrow(IllegalArgumentException::new);
            boolean flag1 = false;
            boolean flag2 = false;
            for (CampsiteAndAmenity ca: camp.getAmenities()) {
                if (ca.getAmenity().getId() == 3) {
                    flag1 = true;
                }
                if (ca.getAmenity().getId() == 4) {
                    flag2 = true;
                }
            }
            assertThat(flag1 && flag2).isEqualTo(true);
        }
    }

    @Test
    @DisplayName("캠핑장 필터링 조회 사업 조건 test")
    public void CampsiteGetListByFilteringInduty() {
        String[] indutyList1 = {"3", "4"};

        Member member = Member.builder().name("test user").uuid(UUID.randomUUID()).build();

        Member savedMember = memberRepository.save(member);

        List<CampsiteListResDTO> result1 = campsiteCustomRepository.getCampsiteListByFiltering(
                "", "", "", new String[0], new String[0],
                indutyList1, new String[0], new String[0], new String[0], savedMember.getUuid());

        for (CampsiteListResDTO campDto: result1) {
            Campsite camp = campsiteRepository.findByUuid(UUID.fromString(campDto.getCampsiteId())).orElseThrow(IllegalArgumentException::new);
            boolean[] isIndustries = new boolean[5];
            for (CampsiteAndIndustry item: camp.getIndustries()) {
                (isIndustries[item.getIndustry().getId()]) = true;
            }

            boolean passInduty = false;
            for (String induty: indutyList1) {
                if (isIndustries[Integer.parseInt(induty)]) {
                    passInduty = true;
                    break;
                }
            }
            assertThat(passInduty).isEqualTo(true);
        }

        String query = "SELECT c FROM Campsite AS c"
                + " WHERE 3 IN (SELECT ci1.industry.id FROM CampsiteAndIndustry AS ci1 WHERE c.id = ci1.campsite.id) "
                + " OR 4 IN (SELECT ci2.industry.id FROM CampsiteAndIndustry AS ci2 WHERE c.id = ci2.campsite.id) ";

        assertThat(result1.size()).isEqualTo(em.createQuery(query, Campsite.class).getResultList().size());

        String[] indutyList2 = {"1", "2", "3", "4"};

        List<CampsiteListResDTO> result2 = campsiteCustomRepository.getCampsiteListByFiltering(
                "", "", "", new String[0], new String[0],
                indutyList2, new String[0], new String[0], new String[0], savedMember.getUuid());

        assertThat(result2.size()).isEqualTo(campsiteRepository.findAll().size());

    }

    @Test
    @DisplayName("캠핑장 필터링 조회 테마 조건 test")
    public void CampsiteGetListByFilteringThema() {
        String[] themaList1 = {"3", "4"};

        Member member = Member.builder().name("test user").uuid(UUID.randomUUID()).build();

        Member savedMember = memberRepository.save(member);

        List<CampsiteListResDTO> result1 = campsiteCustomRepository.getCampsiteListByFiltering(
                "", "", "", new String[0], new String[0],
                new String[0], themaList1, new String[0], new String[0], savedMember.getUuid());

        for (CampsiteListResDTO campDto: result1) {
            Campsite camp = campsiteRepository.findByUuid(UUID.fromString(campDto.getCampsiteId())).orElseThrow(IllegalArgumentException::new);
            boolean[] isthemas = new boolean[13];
            for (CampsiteAndTheme item: camp.getThemes()) {
                (isthemas[item.getTheme().getId()]) = true;
            }

            boolean passThema = false;
            for (String thema: themaList1) {
                if (isthemas[Integer.parseInt(thema)]) {
                    passThema = true;
                    break;
                }
            }
            assertThat(passThema).isEqualTo(true);
        }

        String query = "SELECT c FROM Campsite AS c"
                + " WHERE 3 IN (SELECT ct1.theme.id FROM CampsiteAndTheme AS ct1 WHERE c.id = ct1.campsite.id) "
                + " OR 4 IN (SELECT ct2.theme.id FROM CampsiteAndTheme AS ct2 WHERE c.id = ct2.campsite.id) ";

        assertThat(result1.size()).isEqualTo(em.createQuery(query, Campsite.class).getResultList().size());

        String[] themaList2 = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

        List<CampsiteListResDTO> result2 = campsiteCustomRepository.getCampsiteListByFiltering(
                "", "", "", new String[0], new String[0],
                new String[0], themaList2, new String[0], new String[0], savedMember.getUuid());

        assertThat(result2.size()).isEqualTo(campsiteRepository.findAll().size());
    }

    @Test
    @DisplayName("캠핑장 필터링 조회 운영계절 조건 test")
    public void CampsiteGetListByFilteringOpenSeason() {
        String[] openSeasonList1 = {"3", "4"};

        Member member = Member.builder().name("test user").uuid(UUID.randomUUID()).build();

        Member savedMember = memberRepository.save(member);

        List<CampsiteListResDTO> result1 = campsiteCustomRepository.getCampsiteListByFiltering(
                "", "", "", new String[0], new String[0],
                new String[0], new String[0], new String[0], openSeasonList1, savedMember.getUuid());

        for (CampsiteListResDTO campDto: result1) {
            Campsite camp = campsiteRepository.findByUuid(UUID.fromString(campDto.getCampsiteId())).orElseThrow(IllegalArgumentException::new);
            boolean[] isopenSeasons = new boolean[5];
            for (CampsiteAndOpenSeason item: camp.getOpenSeasons()) {
                (isopenSeasons[item.getOpenSeason().getId()]) = true;
            }

            boolean passOpenSeason = false;
            for (String openSeason: openSeasonList1) {
                if (isopenSeasons[Integer.parseInt(openSeason)]) {
                    passOpenSeason = true;
                    break;
                }
            }
            assertThat(passOpenSeason).isEqualTo(true);
        }


        String query = "SELECT c FROM Campsite AS c"
                + " WHERE 3 IN (SELECT cos1.openSeason.id FROM CampsiteAndOpenSeason AS cos1 WHERE c.id = cos1.campsite.id) "
                + " OR 4 IN (SELECT cos2.openSeason.id FROM CampsiteAndOpenSeason AS cos2 WHERE c.id = cos2.campsite.id) ";

        assertThat(result1.size()).isEqualTo(em.createQuery(query, Campsite.class).getResultList().size());


        String[] openSeasonList2 = {"1", "2", "3", "4"};

        List<CampsiteListResDTO> result2 = campsiteCustomRepository.getCampsiteListByFiltering(
                "", "", "", new String[0], new String[0],
                new String[0], new String[0], new String[0], openSeasonList2, savedMember.getUuid());

        assertThat(result2.size()).isEqualTo(campsiteRepository.findAll().size());
    }

    @Test
    @DisplayName("캠핑장 필터링 조회 내부시설 조건 test")
    public void CampsiteGetListByFilteringFclty() {
        String[] indutyList1 = {"1", "3"};
        String[] fcltyList1 = {"1", "3"};

        Member member = Member.builder().name("test user").uuid(UUID.randomUUID()).build();

        Member savedMember = memberRepository.save(member);

        List<CampsiteListResDTO> result1 = campsiteCustomRepository.getCampsiteListByFiltering(
                "", "", "", fcltyList1, new String[0],
                indutyList1, new String[0], new String[0], new String[0], savedMember.getUuid());

        for (CampsiteListResDTO campDto: result1) {
            Campsite camp = campsiteRepository.findByUuid(UUID.fromString(campDto.getCampsiteId())).orElseThrow(IllegalArgumentException::new);

            boolean passFclty = true;

            boolean isCaravan = false;
            for (CampsiteAndIndustry item: camp.getIndustries()) {
                if (item.getIndustry().getId() == 3) {
                    isCaravan = true;
                    break;
                }
            }

            if (isCaravan) {
                boolean[] isCaravanFclties = new boolean[10];
                for (CampsiteAndCaravanFclty item: camp.getCaravanFclties()) {
                    isCaravanFclties[item.getCaravanFclty().getId()] = true;
                }

                for (String fclty: fcltyList1) {
                    if (!isCaravanFclties[Integer.parseInt(fclty)]) {
                        passFclty = false;
                        break;
                    }
                }
            }
            assertThat(passFclty).isEqualTo(true);
        }


        String[] indutyList2 = {"3", "4"};
        String[] fcltyList2 = {"1", "3"};

        List<CampsiteListResDTO> result2 = campsiteCustomRepository.getCampsiteListByFiltering(
                "", "", "", fcltyList2, new String[0],
                indutyList2, new String[0], new String[0], new String[0], savedMember.getUuid());

        for (CampsiteListResDTO campDto: result2) {
            Campsite camp = campsiteRepository.findByUuid(UUID.fromString(campDto.getCampsiteId())).orElseThrow(IllegalArgumentException::new);
            boolean passFclty = true;

            boolean isCaravan = false;
            boolean isGlamp = false;
            for (CampsiteAndIndustry item: camp.getIndustries()) {
                if (item.getIndustry().getId() == 3) {
                    isCaravan = true;
                    break;
                }
                if (item.getIndustry().getId() == 4) {
                    isGlamp = true;
                    break;
                }
            }

            if (isCaravan) {
                boolean[] isCaravanFclties = new boolean[10];
                for (CampsiteAndCaravanFclty item: camp.getCaravanFclties()) {
                    isCaravanFclties[item.getCaravanFclty().getId()] = true;
                }

                for (String fclty: fcltyList2) {
                    if (!isCaravanFclties[Integer.parseInt(fclty)]) {
                        passFclty = false;
                        break;
                    }
                }
            }

            if (isGlamp) {
                boolean[] isGlampfclties = new boolean[10];
                for (CampsiteAndGlampFclty item: camp.getGlampFclties()) {
                    isGlampfclties[item.getGlampFclty().getId()] = true;
                }

                for (String fclty: fcltyList2) {
                    if (!isGlampfclties[Integer.parseInt(fclty)]) {
                        passFclty = false;
                        break;
                    }
                }
            }
            assertThat(passFclty).isEqualTo(true);
        }
    }

    @Test
    @DisplayName("캠핑장 필터링 조회 복합조건 test")
    public void CampsiteGetListByFilteringComplexCondition() {
        String[] themeList = {"3", "4", "9"};
        String[] indutyList = {"1", "3", "4"};
        String[] allowAnimals = {"가능", "가능(소형견)"};

        Member member = Member.builder().name("test user").uuid(UUID.randomUUID()).build();

        Member savedMember = memberRepository.save(member);

        List<CampsiteListResDTO> result = campsiteCustomRepository.getCampsiteListByFiltering(
                "", "충청북도", "단양군", new String[0], new String[0],
                indutyList, themeList, allowAnimals, new String[0], savedMember.getUuid());

        for (CampsiteListResDTO campDto: result) {
            Campsite camp = campsiteRepository.findByUuid(UUID.fromString(campDto.getCampsiteId())).orElseThrow(IllegalArgumentException::new);

            boolean[] isthemas = new boolean[13];
            for (CampsiteAndTheme item: camp.getThemes()) {
                (isthemas[item.getTheme().getId()]) = true;
            }

            boolean passThema = false;
            for (String thema: themeList) {
                if (isthemas[Integer.parseInt(thema)]) {
                    passThema = true;
                    break;
                }
            }

            boolean[] isIndustries = new boolean[5];
            for (CampsiteAndIndustry item: camp.getIndustries()) {
                (isIndustries[item.getIndustry().getId()]) = true;
            }

            boolean passInduty = false;
            for (String induty: indutyList) {
                if (isIndustries[Integer.parseInt(induty)]) {
                    passInduty = true;
                    break;
                }
            }

            assertThat(passThema && passInduty).isEqualTo(true);
            assertThat(camp.getDoName()).isEqualTo("충청북도");
            assertThat(camp.getSigunguName()).isEqualTo("단양군");
            assertThat(camp.getAllowAnimal()).isIn("가능", "가능(소형견)");
        }
    }


    @Test
    @DisplayName("캠핑장 필터링 조회 필터링 조건이 아무것도 없을 때 test")
    public void CampsiteGetListByFilteringNoCondition() {

        Member member = Member.builder().name("test user").uuid(UUID.randomUUID()).build();

        Member savedMember = memberRepository.save(member);

        List<CampsiteListResDTO> result = campsiteCustomRepository.getCampsiteListByFiltering(
                "", "", "", new String[0], new String[0],
                new String[0], new String[0], new String[0], new String[0], savedMember.getUuid());

        assertThat(result.size()).isEqualTo(campsiteRepository.findAll().size());
    }
}
