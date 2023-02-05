package com.ssafy.campinity.core.service.impl;


import com.ssafy.campinity.core.dto.MyCollectionReqDTO;
import com.ssafy.campinity.core.entity.MyCollection.MyCollection;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.exception.BadRequestException;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import com.ssafy.campinity.core.repository.myCollection.MyCollectionRepository;
import com.ssafy.campinity.core.service.MyCollectionService;
import com.ssafy.campinity.core.utils.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class MyCollectionServiceImpl implements MyCollectionService {

    private final MyCollectionRepository myCollectionRepository;
    private final MemberRepository memberRepository;
    private final ImageUtil imageUtil;

    @Transactional
    @Override
    public MyCollection createMyCollection(MyCollectionReqDTO myCollectionReqDTO, int memberId) {

        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId)
                .orElseThrow(IllegalArgumentException::new);

        String imagePath = "";
        if (myCollectionReqDTO.getFile().getSize() != 0){
            try { imagePath = imageUtil.uploadImage(myCollectionReqDTO.getFile(), "my-collection"); }
            catch (IOException e) { throw new RuntimeException(e); }
        }

        MyCollection myCollection = MyCollection.builder()
                .uuid(UUID.randomUUID())
                .member(member)
                .campsiteName(myCollectionReqDTO.getCampsiteName())
                .content(myCollectionReqDTO.getContent())
                .imagePath(imagePath)
                .date(myCollectionReqDTO.getDate())
                .build();

        try { return myCollectionRepository.save(myCollection); }
        catch (Exception e) { throw new IllegalArgumentException(e); }
    }

    @Transactional
    @Override
    public MyCollection editMyCollection(MyCollectionReqDTO myCollectionReqDTO, String collectionUuid, int memberId) throws FileNotFoundException {

        MyCollection myCollection = myCollectionRepository.findByUuidAndExpiredIsFalse(UUID.fromString(collectionUuid))
                .orElseThrow(IllegalArgumentException::new);
        String imagePath = myCollection.getImagePath();

        if (myCollection.getMember().getId() != memberId) {
            throw new BadRequestException("수정 권한이 없습니다.");
        }

        if (myCollectionReqDTO.getFile().getSize() != 0){
            if (!imagePath.isEmpty()){
                try {
                    boolean result = imageUtil.removeImage(imagePath);
                    if (result) {
                        imagePath = "";
                        myCollection.setImagePath(imagePath);
                    }
                }
                catch (Exception e){
                    throw new FileNotFoundException();
                }
            }
            try { imagePath = imageUtil.uploadImage(myCollectionReqDTO.getFile(), "my-collection"); }
            catch (IOException e) { throw new RuntimeException(e); }
        }

        myCollection.setImagePath(imagePath);
        if (!myCollectionReqDTO.getDate().isEmpty()) myCollection.setDate(myCollectionReqDTO.getDate());
        if (!myCollectionReqDTO.getCampsiteName().isEmpty()) myCollection.setCampsiteName(myCollectionReqDTO.getCampsiteName());
        if (!myCollectionReqDTO.getContent().isEmpty()) myCollection.setContent(myCollectionReqDTO.getContent());

        return myCollectionRepository.save(myCollection);
    }

    @Transactional
    @Override
    public List<MyCollection> getLatestMyCollections(int memberId) {

        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId)
                .orElseThrow(IllegalArgumentException::new);

        List<MyCollection> myCollections = myCollectionRepository.findTop5ByMemberAndExpiredIsFalseOrderByUpdatedAtDesc(member);

        if (myCollections.isEmpty()) return new ArrayList<>();
        return myCollections;
    }

    @Transactional
    @Override
    public List<MyCollection> getMyCollections(int memberId) {

        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId)
                .orElseThrow(IllegalArgumentException::new);

        List<MyCollection> myCollections = myCollectionRepository.findAllByMemberAndExpiredIsFalse(member);

        if (myCollections.isEmpty()) return new ArrayList<>();
        return myCollections;
    }

    @Transactional
    @Override
    public MyCollection getMyCollection(String collectionUuid) {

        return myCollectionRepository.findByUuidAndExpiredIsFalse(UUID.fromString(collectionUuid))
                .orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    @Override
    public void deleteMyCollection(String collectionUuid, int memberId) throws FileNotFoundException {
        MyCollection myCollection = myCollectionRepository.findByUuidAndExpiredIsFalse(UUID.fromString(collectionUuid))
                .orElseThrow(IllegalArgumentException::new);

        if (myCollection.getMember().getId() != memberId) {
            throw new BadRequestException("삭제 권한이 없습니다.");
        }

        String imagePath = myCollection.getImagePath();
        try {
            boolean result = imageUtil.removeImage(imagePath);
            if (result) {
                imagePath = "";
                myCollection.setImagePath(imagePath);
            }
        }
        catch (Exception e){
            throw new FileNotFoundException("이미지 파일 삭제를 실패했습니다.");
        }
        myCollectionRepository.deleteById(myCollection.getId());
    }
}
