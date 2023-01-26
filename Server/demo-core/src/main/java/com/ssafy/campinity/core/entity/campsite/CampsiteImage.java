package com.ssafy.campinity.core.entity.campsite;

import com.ssafy.campinity.core.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CampsiteImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String imagePath;

    @ManyToOne
    private Campsite campsite;

    @Builder
    public CampsiteImage(String imagePath, Campsite campsite) {
        this.imagePath = imagePath;
        this.campsite = campsite;
    }
}
