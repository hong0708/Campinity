package com.ssafy.campinity.core.entity.member;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.campinity.core.entity.BaseEntity;
import com.ssafy.campinity.core.entity.answer.Answer;
import com.ssafy.campinity.core.entity.campsite.CampsiteScrap;
import com.ssafy.campinity.core.entity.curation.CurationScrap;
import com.ssafy.campinity.core.entity.fcmToken.FcmToken;
import com.ssafy.campinity.core.entity.message.LikeMessage;
import com.ssafy.campinity.core.entity.message.Message;
import com.ssafy.campinity.core.entity.question.Question;
import com.ssafy.campinity.core.entity.review.Review;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@SQLDelete(sql = "update member set expired = true where id = ?")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "char(36)")
    @Type(type = "uuid-char")
    private UUID uuid;

//    @OneToMany
//    @JoinColumn(name = "member_id")
//    @ToString.Exclude
//    private List<Message> messages = new ArrayList<>();
//
//    @OneToMany
//    @JoinColumn(name = "member_id")
//    @ToString.Exclude
//    private List<LikeMessage> likeMessages = new ArrayList<>();
//
//    @OneToMany
//    @JoinColumn(name = "member_id")
//    @ToString.Exclude
//    private List<CampsiteScrap> campsiteScraps = new ArrayList<>();
//
//    @OneToMany
//    @JoinColumn(name = "member_id")
//    @ToString.Exclude
//    private List<Review> reviews = new ArrayList<>();
//
//    @OneToMany
//    @JoinColumn(name = "member_id")
//    @ToString.Exclude
//    private List<Question> questions = new ArrayList<>();
//
//    @OneToMany
//    @JoinColumn(name = "member_id")
//    @ToString.Exclude
//    private List<Answer> answers = new ArrayList<>();
//
//    @OneToMany
//    @JoinColumn(name = "member_id")
//    @ToString.Exclude
//    private List<CurationScrap> curationScraps = new ArrayList<>();

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "fcmToken_id")
    private FcmToken fcmToken = new FcmToken();

    private String name;

    private String email;

    private String profileImage;

    private Boolean expired = false;

    @Builder
    public Member(UUID uuid, FcmToken fcmToken, String name, String email, String profileImage) {
        this.uuid = uuid;
        this.fcmToken = fcmToken;
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
    }
//
//    public void addUserScrap(CampsiteScrap campsiteScrap) {
//        this.getCampsiteScraps().add(campsiteScrap);
//    }
//
//    public void addUserReview(Review review) {
//        this.getReviews().add(review);
//    }
}
