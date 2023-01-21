package com.ssafy.campinity.core.service;

import com.ssafy.campinity.core.entity.member.Member;

import java.util.UUID;

public interface MemberService {
    Member findMemberById(Integer id);
    Member findMemberByUUID(UUID uuid);
    Member save(Member member);
}
