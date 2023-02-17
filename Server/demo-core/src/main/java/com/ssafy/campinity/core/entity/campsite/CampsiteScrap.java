package com.ssafy.campinity.core.entity.campsite;

import com.ssafy.campinity.core.entity.BaseEntity;
import com.ssafy.campinity.core.entity.member.Member;
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

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Campsite campsite;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Member member;

    @Builder
    public CampsiteScrap(Campsite campsite, Member member) {
        this.campsite = campsite;
        this.member = member;
    }
}
