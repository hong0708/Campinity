package com.ssafy.campinity.core.entity.review;

import com.ssafy.campinity.core.entity.BaseEntity;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.member.Member;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Getter
@SQLDelete(sql = "UPDATE review SET expired = true WHERE review.id = ?")
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "char(36)")
    @Type(type = "uuid-char")
    private UUID uuid;

    private String content;

    private int rate;

    private Boolean expired;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Campsite campsite;

    @Builder
    public Review(UUID uuid, String content, int rate, Member member, Campsite campsite) {
        this.uuid = uuid;
        this.content = content;
        this.rate = rate;
        this.member = member;
        this.campsite = campsite;
        this.expired = false;
    }
}
