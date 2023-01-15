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
public class Industry extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String industryName;

    @OneToMany
    @JoinColumn(name = "industry_id")
    @ToString.Exclude
    private List<CampsiteAndIndustry> campsiteAndIndustries = new ArrayList<>();

    @Builder
    public Industry(String industryName, List<CampsiteAndIndustry> campsiteAndIndustries) {
        this.industryName = industryName;
        this.campsiteAndIndustries = campsiteAndIndustries;
    }
}
