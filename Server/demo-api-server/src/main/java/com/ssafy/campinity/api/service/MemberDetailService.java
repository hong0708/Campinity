package com.ssafy.campinity.api.service;

import com.ssafy.campinity.api.config.security.jwt.MemberDetails;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public MemberDetailService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findMemberByEmail(username).orElseThrow
                (() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        return new MemberDetails(member);
    }
}
