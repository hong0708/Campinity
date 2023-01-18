package com.ssafy.campinity.core.entity.message;


import com.ssafy.campinity.core.entity.BaseEntity;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;


@Data
@NoArgsConstructor
@Entity
public class Message extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "char(36)")
    @Type(type = "uuid-char")
    private UUID uuid;

    @ManyToOne
    private Campsite campsite;

    @ManyToOne
    private User user;

    @Enumerated(value = EnumType.STRING)
    private MessageCategory messageCategory;

    @OneToMany
    @JoinColumn(name = "message_id")
    private List<LikeMessage> likeMessages = new ArrayList<>();

    private String content;

    private String imagePath;

    private Double longitude;

    private Double latitude;

    private boolean isDeleted = false;

    @Builder
    public Message(UUID uuid, Campsite campsite, User user, MessageCategory messageCategory, List<LikeMessage> likeMessages, String content, Double longitude, Double latitude, boolean isDeleted) {
        this.uuid = uuid;
        this.campsite = campsite;
        this.user = user;
        this.messageCategory = messageCategory;
        this.likeMessages = likeMessages;
        this.content = content;
        this.longitude = longitude;
        this.latitude = latitude;
        this.isDeleted = isDeleted;
    }
}
