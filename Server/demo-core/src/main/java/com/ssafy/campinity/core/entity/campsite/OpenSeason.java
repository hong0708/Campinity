package com.ssafy.campinity.core.entity.campsite;


import com.ssafy.campinity.core.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
public class OpenSeason extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String seasonName;

    @OneToMany
    @JoinColumn(name = "open_season_id")
    @ToString.Exclude
    private List<CampsiteAndOpenSeason> campsiteAndOpenSeasons = new ArrayList<>();


    @Builder
    public OpenSeason(String seasonName, List<CampsiteAndOpenSeason> campsiteAndOpenSeasons) {
        this.seasonName = seasonName;
        this.campsiteAndOpenSeasons = campsiteAndOpenSeasons;
    }
}
