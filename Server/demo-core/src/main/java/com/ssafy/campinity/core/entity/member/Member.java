package com.ssafy.campinity.core.entity.member;

import com.ssafy.campinity.core.entity.BaseEntity;
import com.ssafy.campinity.core.entity.fcm.FcmToken;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
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

    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @ToString.Exclude
    private List<FcmToken> fcmTokenList;

    private String name;

    private String email;

    private String profileImage;

    private Boolean expired = false;

    @Builder
    public Member(UUID uuid, FcmToken fcmTokenList, String name, String email, String profileImage) {
        this.uuid = uuid;
        this.fcmTokenList = fcmTokenList == null ? new ArrayList<>() : List.of(fcmTokenList);
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
    }

    public void addFcmToken(FcmToken fcmToken){
        this.fcmTokenList.add(fcmToken);
    }
    public void removeFcmToken(FcmToken fcmToken){
        this.fcmTokenList.remove(fcmToken);
    }

}
