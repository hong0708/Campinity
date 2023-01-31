package com.ssafy.campinity.core.entity.curation;

import com.ssafy.campinity.core.entity.BaseEntity;
import com.ssafy.campinity.core.entity.member.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CurationScrap extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Curation curation;

    @Builder
    public CurationScrap(Member member, Curation curation) {
        this.member = member;
        this.curation = curation;
    }
}
