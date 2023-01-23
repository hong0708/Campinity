package com.ssafy.campinity.api.config.security.jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
public class Subject {

    private UUID uuid;
    private String email;
    private String nickname;
    private String type;

    public Subject() {

    }

    private Subject(UUID uuid, String email, String nickname, String type) {
        this.uuid = uuid;
        this.email = email;
        this.nickname = nickname;
        this.type = type;
    }

    public static Subject atk(UUID uuid, String email, String nickname) {
        return new Subject(uuid, email, nickname, "ATK");
    }

    public static Subject rtk(UUID uuid, String email, String nickname) {
        return new Subject(uuid, email, nickname, "RTK");
    }
}