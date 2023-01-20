package com.ssafy.campinity.core.entity.message;

import com.ssafy.campinity.core.entity.BaseEntity;
import com.ssafy.campinity.core.entity.member.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class LikeMessage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Message message;

    @ManyToOne
    private Member member;

    private Boolean likeCheck;

    @Builder
    public LikeMessage(Message message, Member member, Boolean likeCheck) {
        this.message = message;
        this.member = member;
        this.likeCheck = likeCheck;
    }

    public Boolean changeLikeCheck() {
        this.likeCheck = !this.likeCheck;
        return this.likeCheck;
    }

}
