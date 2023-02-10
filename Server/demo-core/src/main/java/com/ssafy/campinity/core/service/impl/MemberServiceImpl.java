package com.ssafy.campinity.core.service.impl;

import com.ssafy.campinity.core.dto.EditMemberInfoReqDTO;
import com.ssafy.campinity.core.dto.MemberProfileImageResDTO;
import com.ssafy.campinity.core.dto.MemberResDTO;
import com.ssafy.campinity.core.dto.ProfileResDTO;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import com.ssafy.campinity.core.service.MemberService;
import com.ssafy.campinity.core.utils.ErrorMessageEnum;
import com.ssafy.campinity.core.utils.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final EntityManager em;

    private final ImageUtil imageUtil;

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
    public Boolean checkNicknameDuplicate(String currentNickName, Integer memberId) {
        Boolean isExist = false;
        Optional<Member> member = memberRepository.findByNameAndIdNot(currentNickName, memberId);

        if (!member.isEmpty()) {
            isExist = true;
        }

        return isExist;
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

    @Transactional
    @Override
    public ProfileResDTO getMemberProfile(Integer memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new NoSuchElementException(ErrorMessageEnum.USER_NOT_EXIST.getMessage()));
        return ProfileResDTO.builder().member(member).build();
    }

    @Override
    public MemberResDTO editUserInfo(EditMemberInfoReqDTO editMemberInfoReqDTO, Member member) throws IOException {
        MultipartFile profileImg = editMemberInfoReqDTO.getProfileImg();
        String nickName = editMemberInfoReqDTO.getNickName();
        String profileImgPath = member.getProfileImage();

        if (editMemberInfoReqDTO.getIsChanged()) {
            // 기존에 프로필 이미지 존재하고, 업로드 된 파일이 존재하는 경우
            if (!ObjectUtils.isEmpty(member.getProfileImage()) && profileImg.getSize() != 0) {
                // 기존 프로필 이미지 제거
                imageUtil.removeImage(member.getProfileImage());
                profileImgPath = imageUtil.uploadImage(profileImg, "member");
            } else {
                // 업로드된 파일이 null인 경우 기존 프로필 지우고 경로 "" 설정
                if (profileImg.getSize() == 0) {
                    imageUtil.removeImage(member.getProfileImage());
                    profileImgPath = "";
                }
                // 기존 프로필 이미지가 존재하지 않은데, profile이미지가 업로드 된 경우
                else if (ObjectUtils.isEmpty(member.getProfileImage())) {
                    profileImgPath = imageUtil.uploadImage(profileImg, "member");
                }
            }
        }

        // isChanged false인 경우 기존 데이터 그대로 넣고 저장
        member.setProfileImage(profileImgPath);
        member.setName(nickName);;
        memberRepository.save(member);
        return MemberResDTO.builder()
                .nickName(member.getName())
                .profileImg(member.getProfileImage())
                .email(member.getEmail()).build();
    }

    @Override
    public MemberProfileImageResDTO getMemberProfileImage(int memberId) {
        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId).orElseThrow(() ->
                new NoSuchElementException(ErrorMessageEnum.USER_NOT_EXIST.getMessage()));

        return MemberProfileImageResDTO.builder().profile(member.getProfileImage()).build();
    }
}
