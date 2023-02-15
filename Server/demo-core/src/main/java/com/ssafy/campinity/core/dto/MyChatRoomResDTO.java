package com.ssafy.campinity.core.dto;


import com.ssafy.campinity.core.entity.chat.ChatRoom;
import com.ssafy.campinity.core.entity.member.Member;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyChatRoomResDTO {

    private String roomId;
    private String otherProfilePath;
    private String name; // campsitename에서 {상대 닉네임}님과의 대화
    private String subject; //fcm message body
    private String createdAt;
    private Boolean expired;

    @Builder
    public MyChatRoomResDTO(ChatRoom room, String otherProfilePath, String otherNickname) {
        this.roomId = room.getId();
        this.otherProfilePath = otherProfilePath;
        this.name = room.getName() + "에서 " + otherNickname + " 님과의 대화";
        this.subject = room.getFcmMessageBody();
        this.createdAt = room.getCreatedAt().toString();
        this.expired = room.getExpired();
    }
}
