// data backup을 위한 테이블

//package com.ssafy.campinity.demo.batch.entity;
//
//import lombok.*;
//import org.hibernate.annotations.Type;
//
//import javax.persistence.*;
//import java.util.UUID;
//
//@Getter
//@NoArgsConstructor
//@ToString
//@EqualsAndHashCode
//@Entity
//public class BackupMessage {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    @Column(columnDefinition = "char(36)")
//    @Type(type = "uuid-char")
//    private UUID uuid;
//
//    private UUID campsiteId;
//
//    private UUID memberId;
//
//    private String messageCategory;
//
//    private int likeMessageCnt;
//
//    private String content;
//
//    private Double longitude;
//
//    private Double latitude;
//
//    @Builder
//    public BackupMessage(UUID uuid, UUID campsiteId, UUID memberId, String messageCategory, int likeMessageCnt, String content, Double longitude, Double latitude) {
//        this.uuid = uuid;
//        this.campsiteId = campsiteId;
//        this.memberId = memberId;
//        this.messageCategory = messageCategory;
//        this.likeMessageCnt = likeMessageCnt;
//        this.content = content;
//        this.longitude = longitude;
//        this.latitude = latitude;
//    }
//}
