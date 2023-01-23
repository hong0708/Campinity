package com.ssafy.campinity.core.entity.MyCollection;


import com.ssafy.campinity.core.entity.BaseEntity;
import com.ssafy.campinity.core.entity.member.Member;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@SQLDelete(sql =  "update my_collection set expired = true where id = ?")
public class MyCollection extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "char(36)")
    @Type(type = "uuid-char")
    private UUID uuid;

    @ManyToOne
    private Member member;

    private String campsiteName;

    private String content;

    private String imagePath;

    private String date;

    private Boolean expired = false;

    @Builder
    public MyCollection(UUID uuid, Member member, String campsiteName, String content, String imagePath, String date) {
        this.uuid = uuid;
        this.member = member;
        this.campsiteName = campsiteName;
        this.content = content;
        this.imagePath = imagePath;
        this.date = date;
    }
}
