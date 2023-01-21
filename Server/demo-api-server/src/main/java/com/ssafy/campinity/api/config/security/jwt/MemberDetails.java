package com.ssafy.campinity.api.config.security.jwt;

import com.ssafy.campinity.core.entity.member.Member;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class MemberDetails extends User {

    private final Member member;

    public MemberDetails(Member member) {
        super(member.getEmail(), "", List.of(new SimpleGrantedAuthority("USER")));
        this.member = member;
    }
}
