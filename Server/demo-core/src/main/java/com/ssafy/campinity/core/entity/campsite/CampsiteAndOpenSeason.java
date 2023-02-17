package com.ssafy.campinity.core.entity.campsite;


import com.ssafy.campinity.core.entity.BaseEntity;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.campsite.OpenSeason;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
public class CampsiteAndOpenSeason extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @ToString.Exclude
    private Campsite campsite;

    @ManyToOne
    @ToString.Exclude
    private OpenSeason openSeason;

    @Builder
    public CampsiteAndOpenSeason(Campsite campsite, OpenSeason openSeason) {
        this.campsite = campsite;
        this.openSeason = openSeason;
    }
}
