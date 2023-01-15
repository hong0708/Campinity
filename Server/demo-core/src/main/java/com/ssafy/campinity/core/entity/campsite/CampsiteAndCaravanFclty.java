package com.ssafy.campinity.core.entity.campsite;


import com.ssafy.campinity.core.entity.BaseEntity;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.campsite.CaravanFclty;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
public class CampsiteAndCaravanFclty extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Campsite campsite;

    @ManyToOne
    private CaravanFclty caravanFclty;

    @Builder
    public CampsiteAndCaravanFclty(Campsite campsite, CaravanFclty caravanFclty) {
        this.campsite = campsite;
        this.caravanFclty = caravanFclty;
    }
}
