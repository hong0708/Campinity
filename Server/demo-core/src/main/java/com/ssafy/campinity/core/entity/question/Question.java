package com.ssafy.campinity.core.entity.question;

import com.ssafy.campinity.core.entity.BaseEntity;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.member.Member;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@SQLDelete(sql = "UPDATE question SET expired = true WHERE question.id = ?")
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "char(36)")
    @Type(type = "uuid-char")
    private UUID uuid;

    private String content;

    private Boolean expired;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Campsite campsite;

    @Builder
    public Question(UUID uuid, String content, Member member, Campsite campsite) {
        this.uuid = uuid;
        this.content = content;
        this.member = member;
        this.campsite = campsite;
        this.expired = false;
    }
}
