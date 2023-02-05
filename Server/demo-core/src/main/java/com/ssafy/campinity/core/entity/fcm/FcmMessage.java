package com.ssafy.campinity.core.entity.fcm;


import com.ssafy.campinity.core.entity.BaseEntity;
import com.ssafy.campinity.core.entity.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
//@SQLDelete(sql = "update fcm_message set expired = true where id = ?")
public class FcmMessage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private UUID uuid;

    private String title;

    private String body;

    private String hiddenBody;

    @OneToOne
    @ToString.Exclude
    private Member member;

    private String appointeeToken;

    private boolean expired;

    @Builder
    public FcmMessage(String title, String body, String hiddenBody, Member member) {
        this.title = title;
        this.uuid = UUID.randomUUID();
        this.body = body;
        this.hiddenBody = hiddenBody;
        this.member = member;
        this.expired = false;
    }

    public void appointReciever(String fcmToken){
        this.appointeeToken = fcmToken;
    }
}
