package com.ssafy.campinity.core.entity.campsite;


import com.ssafy.campinity.core.entity.BaseEntity;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.campsite.Amenity;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
public class CampsiteAndAmenity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Campsite campsite;

    @ManyToOne
    private Amenity amenity;

    @Builder
    public CampsiteAndAmenity(Campsite campsite, Amenity amenity) {
        this.campsite = campsite;
        this.amenity = amenity;
    }
}
