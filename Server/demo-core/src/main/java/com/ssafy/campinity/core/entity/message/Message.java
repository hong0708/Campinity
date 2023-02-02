package com.ssafy.campinity.core.entity.message;


import com.ssafy.campinity.core.entity.BaseEntity;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.member.Member;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@SQLDelete(sql = "update message set expired = true where id = ?")
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
    private Member member;

    @Enumerated(value = EnumType.STRING)
    private MessageCategory messageCategory;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "message_id")
    @ToString.Exclude
    private List<LikeMessage> likeMessages = new ArrayList<>();

    private String content;

    private String imagePath;

    private Double longitude;

    private Double latitude;

    private Boolean expired;

    @Builder
    public Message(UUID uuid, Campsite campsite, Member member, String messageCategory, String content, String imagePath, Double longitude, Double latitude) {
        this.uuid = uuid;
        this.campsite = campsite;
        this.member = member;
        this.messageCategory = MessageCategory.fromParam(messageCategory);
        this.content = content;
        this.imagePath = imagePath;
        this.longitude = longitude;
        this.latitude = latitude;
        this.expired = false;
    }

    public void removeLikeMessage(LikeMessage likeMessage){
        this.likeMessages.remove(likeMessage);
    }
}
