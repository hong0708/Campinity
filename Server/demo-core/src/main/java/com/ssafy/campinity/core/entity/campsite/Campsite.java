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
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Where(clause = "is_deleted = false")
@Builder
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

    private String intro;

    private String lineIntro;

    private String experienceProgram;

    private String subFacilityEtc;

    private String dayOperation;

    private boolean allowAnimal;

    private boolean isDeleted = false;

}
