package com.ssafy.campinity.core.entity.campsite;


import com.ssafy.campinity.core.entity.BaseEntity;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.campsite.Theme;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
public class CampsiteAndTheme extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Campsite campsite;

    @ManyToOne
    private Theme theme;

    @Builder
    public CampsiteAndTheme(Campsite campsite, Theme theme) {
        this.campsite = campsite;
        this.theme = theme;
    }
}
