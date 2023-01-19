package com.ssafy.campinity.core.entity.review;

import com.ssafy.campinity.core.entity.BaseEntity;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.user.User;
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
@SQLDelete(sql = "UPDATE review SET is_deleted = true WHERE review.id = ?")
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "char(36)")
    @Type(type = "uuid-char")
    private UUID uuid;

    private String content;

    private int rate;

    private Boolean isDeleted = false;

    @ManyToOne
    private User user;

    @ManyToOne
    private Campsite campsite;

    @Builder
    public Review(UUID uuid, String content, int rate, User user, Campsite campsite) {
        this.uuid = uuid;
        this.content = content;
        this.rate = rate;
        this.user = user;
        this.campsite = campsite;
    }
}
