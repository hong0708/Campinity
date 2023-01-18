package com.ssafy.campinity.core.entity.user;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.campinity.core.entity.BaseEntity;
import com.ssafy.campinity.core.entity.campsite.CampsiteScrap;
import com.ssafy.campinity.core.entity.message.LikeMessage;
import com.ssafy.campinity.core.entity.message.Message;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "char(36)")
    @Type(type = "uuid-char")
    private UUID uuid;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Message> messages = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<LikeMessage> likeMessages = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<CampsiteScrap> scraps = new ArrayList<>();

    private String fcmToken;

    private String name;

    private String email;

    private String profileImage;

    private boolean isDeleted;

    @Builder
    public User(UUID uuid, String fcmToken, String name, String email, String profileImage, boolean isDeleted) {
        this.uuid = uuid;
        this.fcmToken = fcmToken;
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
        this.isDeleted = isDeleted;
    }

    public void addCampsiteScrap(CampsiteScrap campsiteScrap) {
        this.getScraps().add(campsiteScrap);
    }
}
