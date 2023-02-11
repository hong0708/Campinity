package com.ssafy.campinity.core.entity.campsite;

import com.ssafy.campinity.core.entity.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CampsiteImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String imagePath;

    private String thumbnailImage;

    @ManyToOne(fetch = FetchType.LAZY)
    private Campsite campsite;

    @Builder
    public CampsiteImage(String imagePath, Campsite campsite, String thumbnailImage) {
        this.imagePath = imagePath;
        this.campsite = campsite;
        this.thumbnailImage = thumbnailImage;
    }
}
