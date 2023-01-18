package com.ssafy.campinity.core.entity.campsite;

import com.ssafy.campinity.core.entity.BaseEntity;
import com.ssafy.campinity.core.entity.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CampsiteScrap extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Campsite campsite;

    @ManyToOne
    private User user;

    private Boolean scrapType;

    @Builder
    public CampsiteScrap(Campsite campsite, User user, Boolean scrapType) {
        this.campsite = campsite;
        this.user = user;
        this.scrapType = scrapType;
    }


    public void changeScrapType() {
        this.scrapType = !this.scrapType;
    }
}
