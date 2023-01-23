package com.ssafy.campinity.core.service.impl;


import com.ssafy.campinity.core.dto.MyCollectionReqDTO;
import com.ssafy.campinity.core.entity.MyCollection.MyCollection;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import com.ssafy.campinity.core.repository.myCollection.MyCollectionRepository;
import com.ssafy.campinity.core.service.MyCollectionService;
import com.ssafy.campinity.core.utils.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
    public MyCollection createMyCollection(MyCollectionReqDTO myCollectionReqDTO, String memberUuid) {

        Member member = memberRepository.findMemberByUuidAndExpiredIsFalse(UUID.fromString(memberUuid))
                .orElseThrow(IllegalArgumentException::new);

        String imagePath = "";
        try { imagePath = imageUtil.uploadImage(myCollectionReqDTO.getFile(), "myCollection"); }
        catch (IOException e) { throw new RuntimeException(e); }

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
    public MyCollection editMyCollection(MyCollectionReqDTO myCollectionReqDTO, String collectionUuid) throws FileNotFoundException {

        MyCollection myCollection = myCollectionRepository.findByUuidAndExpiredIsFalse(UUID.fromString(collectionUuid))
                .orElseThrow(IllegalArgumentException::new);
        String imagePath = myCollection.getImagePath();

        if (!myCollectionReqDTO.getFile().isEmpty()){
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
            try { imagePath = imageUtil.uploadImage(myCollectionReqDTO.getFile(), "myCollection"); }
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
    public List<MyCollection> getMyCollections(String memberUuid) {

        Member member = memberRepository.findMemberByUuidAndExpiredIsFalse(UUID.fromString(memberUuid))
                .orElseThrow(IllegalArgumentException::new);

        List<MyCollection> myCollections = myCollectionRepository.findByMemberAndExpiredIsFalse(member);

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
    public void deleteMyCollection(String collectionUuid) throws FileNotFoundException {
        MyCollection myCollection = myCollectionRepository.findByUuidAndExpiredIsFalse(UUID.fromString(collectionUuid))
                .orElseThrow(IllegalArgumentException::new);

        String imagePath = myCollection.getImagePath();
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

        myCollectionRepository.deleteById(myCollection.getId());

    }
}
