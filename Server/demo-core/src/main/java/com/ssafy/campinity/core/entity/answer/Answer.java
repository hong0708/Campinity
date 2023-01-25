package com.ssafy.campinity.core.entity.answer;

import com.ssafy.campinity.core.entity.BaseEntity;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.entity.question.Question;
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
@SQLDelete(sql = "UPDATE answer SET expired = true WHERE answer.id = ?")
public class Answer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "char(36)")
    @Type(type = "uuid-char")
    private UUID uuid;

    private String content;

    private Boolean expired;

    @ManyToOne
    private Question question;

    @ManyToOne
    private Member member;

    @Builder
    public Answer(UUID uuid, String content, Question question, Member member) {
        this.uuid = uuid;
        this.content = content;
        this.expired = false;
        this.question = question;
        this.member = member;
    }
}
