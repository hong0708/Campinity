package com.ssafy.campinity.core.jwt;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Subject {

    private final UUID uuid;

    private final String email;

    private final String nickname;

    private final String type;

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