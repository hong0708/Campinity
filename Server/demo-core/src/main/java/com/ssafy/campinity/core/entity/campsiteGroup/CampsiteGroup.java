package com.ssafy.campinity.core.entity.campsiteGroup;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.util.Map;

@Getter
@Setter
@RedisHash(value = "group", timeToLive = -1L)
public class CampsiteGroup {

    /*
    * id : group:{$campsiteUuid}
    * memberInCamp : {캠핑장 알람에 구독한 유저uuid : 유저 fcmToken}
     */
    @Id
    private String id;
    private Map<String, String> membersInCamp;

    public CampsiteGroup(String id, Map<String, String> membersInCamp) {
        this.id = id;
        this.membersInCamp = membersInCamp;
    }
}
