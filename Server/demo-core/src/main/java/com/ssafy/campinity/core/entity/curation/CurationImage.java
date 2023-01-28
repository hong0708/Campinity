package com.ssafy.campinity.core.entity.curation;


import com.ssafy.campinity.core.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CurationImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Curation curation;

    private String imagePath;

    @Builder
    public CurationImage(Curation curation, String imagePath) {
        this.curation = curation;
        this.imagePath = imagePath;
    }
}
