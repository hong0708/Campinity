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
public class Amenity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String amenityName;

    @OneToMany
    @JoinColumn(name = "amenity_id")
    @ToString.Exclude
    private List<CampsiteAndAmenity> campsiteAndAmenities = new ArrayList<>();

    @Builder
    public Amenity(int id, String amenityName, List<CampsiteAndAmenity> campsiteAndAmenities) {
        this.id = id;
        this.amenityName = amenityName;
        this.campsiteAndAmenities = campsiteAndAmenities;
    }
}
