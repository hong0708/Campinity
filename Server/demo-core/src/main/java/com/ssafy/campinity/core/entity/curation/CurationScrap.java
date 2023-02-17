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
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "curation_id")
    private Curation curation;

    @Builder
    public CurationScrap(Member member, Curation curation) {
        this.member = member;
        this.curation = curation;
    }
}
