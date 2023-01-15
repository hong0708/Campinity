package com.ssafy.campinity.core.entity.campsite;


import com.ssafy.campinity.core.entity.BaseEntity;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.campsite.GlampFclty;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
public class CampsiteAndGlampFclty extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Campsite campsite;

    @ManyToOne
    private GlampFclty glampFclty;

    @Builder
    public CampsiteAndGlampFclty(Campsite campsite, GlampFclty glampFclty) {
        this.campsite = campsite;
        this.glampFclty = glampFclty;
    }
}
