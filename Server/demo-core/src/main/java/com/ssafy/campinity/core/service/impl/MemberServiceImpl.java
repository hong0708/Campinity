package com.ssafy.campinity.core.service.impl;

import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import com.ssafy.campinity.core.service.MemberService;
import com.ssafy.campinity.core.utils.ErrorMessageEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final EntityManager em;

    @Override
    public Member findMemberById(Integer id) {
        return memberRepository.findById(id).orElseThrow(() -> new NoSuchElementException(ErrorMessageEnum.USER_NOT_EXIST.getMessage()));
    }

    @Override
    public Member findMemberByUUID(UUID uuid) {
        return memberRepository.findMemberByUuidAndExpiredIsFalse(uuid).orElseThrow(() -> new NoSuchElementException(ErrorMessageEnum.USER_NOT_EXIST.getMessage()));
    }

    @Override
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Boolean checkNicknameDuplicate(String nickname) {
        return memberRepository.existsByName(nickname);
    }

    @Override
    @Transactional
    public Integer deleteMemberHard(Integer memberId) {
        try {
            String jpql = "delete from Member m where m.id =:memberId";
            Query query = em.createQuery(jpql);
            query.setParameter("memberId", memberId);
            Integer rows = query.executeUpdate();
            return rows;
        } catch (OptimisticLockingFailureException e) {
            throw new OptimisticLockingFailureException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
