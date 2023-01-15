package com.ssafy.campinity.core.entity.campsite;

import com.ssafy.campinity.core.entity.BaseEntity;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.campsite.Industry;
import lombok.*;

import javax.persistence.*;


@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
public class CampsiteAndIndustry extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Campsite campsite;

    @ManyToOne
    private Industry industry;

    @Builder
    public CampsiteAndIndustry(Campsite campsite, Industry industry) {
        this.campsite = campsite;
        this.industry = industry;
    }
}
