package com.ssafy.campinity.core.entity.campsite;


import com.ssafy.campinity.core.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Where(clause = "is_deleted = false")
public class Campsite extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "char(36)")
    @Type(type = "uuid-char")
    private UUID uuid;

    @OneToMany
    @JoinColumn(name = "campsite_id")
    @ToString.Exclude
    private List<CampsiteAndAmenity> amenities = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "campsite_id")
    @ToString.Exclude
    private List<CampsiteAndCaravanFclty> caravanfclties = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "campsite_id")
    @ToString.Exclude
    private List<CampsiteAndGlampFclty> glampfclties = new ArrayList<>();


    @OneToMany
    @JoinColumn(name = "campsite_id")
    @ToString.Exclude
    private List<CampsiteAndOpenSeason> openSeasons = new ArrayList<>();


    @OneToMany
    @JoinColumn(name = "campsite_id")
    @ToString.Exclude
    private List<CampsiteAndTheme> themes = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "campsite_id")
    @ToString.Exclude
    private List<CampsiteAndIndustry> industries = new ArrayList<>();


    private int contentId;

    private String campName;

    private String firstImageUrl;

    private String address;

    private String doName;

    private String sigunguName;

    private Double latitude;

    private Double longitude;

    private String phoneNumber;

    private String homepage;

    private String reserveType;

    @Lob
    private String intro;

    private String lineIntro;

    private String experienceProgram;

    private String subFacilityEtc;

    private String dayOperation;

    private String allowAnimal;

    private boolean isDeleted = false;

    @Builder
    public Campsite(UUID uuid, List<CampsiteAndAmenity> amenities, List<CampsiteAndCaravanFclty> caravanfclties, List<CampsiteAndGlampFclty> glampfclties, List<CampsiteAndOpenSeason> openSeasons, List<CampsiteAndTheme> themes, List<CampsiteAndIndustry> industries, int contentId, String campName, String firstImageUrl, String address, String doName, String sigunguName, Double latitude, Double longitude, String phoneNumber, String homepage, String reserveType, String intro, String lineIntro, String experienceProgram, String subFacilityEtc, String dayOperation, String allowAnimal) {
        this.uuid = uuid;
        this.amenities = amenities;
        this.caravanfclties = caravanfclties;
        this.glampfclties = glampfclties;
        this.openSeasons = openSeasons;
        this.themes = themes;
        this.industries = industries;
        this.contentId = contentId;
        this.campName = campName;
        this.firstImageUrl = firstImageUrl;
        this.address = address;
        this.doName = doName;
        this.sigunguName = sigunguName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phoneNumber = phoneNumber;
        this.homepage = homepage;
        this.reserveType = reserveType;
        this.intro = intro;
        this.lineIntro = lineIntro;
        this.experienceProgram = experienceProgram;
        this.subFacilityEtc = subFacilityEtc;
        this.dayOperation = dayOperation;
        this.allowAnimal = allowAnimal;
    }

}
