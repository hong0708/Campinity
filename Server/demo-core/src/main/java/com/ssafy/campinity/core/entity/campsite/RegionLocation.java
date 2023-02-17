package com.ssafy.campinity.core.entity.campsite;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter
public class RegionLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String doName;

    private String sigunguName;

    private double latitude;

    private double longitude;


    @Builder
    public RegionLocation(String doName, String sigunguName, double latitude, double longitude) {
        this.doName = doName;
        this.sigunguName = sigunguName;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
