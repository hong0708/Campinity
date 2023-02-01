package com.ssafy.campinity.core.entity.message;

import com.ssafy.campinity.core.entity.BaseEntity;
import com.ssafy.campinity.core.entity.member.Member;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
public class LikeMessage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @ToString.Exclude
    private Message message;

    @ManyToOne
    @ToString.Exclude
    private Member member;

    @Builder
    public LikeMessage(Message message, Member member) {
        message.getLikeMessages().add(this);
        this.message = message;
        this.member = member;
    }
}
