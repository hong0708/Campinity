package com.ssafy.campinity.core.service;

import com.ssafy.campinity.core.dto.EditMemberInfoReqDTO;
import com.ssafy.campinity.core.dto.MemberResDTO;
import com.ssafy.campinity.core.dto.ProfileResDTO;
import com.ssafy.campinity.core.entity.member.Member;

import java.io.IOException;
import java.util.UUID;

public interface MemberService {
    Member findMemberById(Integer id);
    Member findMemberByUUID(UUID uuid);
    Member save(Member member);
    Boolean checkNicknameDuplicate(String nickname, Integer memberId);
    Integer deleteMemberHard(Integer memberId);
    ProfileResDTO getMemberProfile(Integer id);
    MemberResDTO editUserInfo(EditMemberInfoReqDTO editMemberInfoReqDTO, Member member) throws IOException;
}
