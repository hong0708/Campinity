package com.ssafy.campinity.core.service.impl;

import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import com.ssafy.campinity.core.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member findMemberById(Integer id) {
        return memberRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Member findMemberByUUID(UUID uuid) {
        return memberRepository.findMemberByUuidAndExpiredIsFalse(uuid).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Member save(Member member) {
        return memberRepository.save(member);
    }
}
