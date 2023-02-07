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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Transactional
@RequiredArgsConstructor
@Service
public class MyCollectionServiceImpl implements MyCollectionService {

    private final MyCollectionRepository myCollectionRepository;
    private final MemberRepository memberRepository;
    private final ImageUtil imageUtil;

    @Override
    public MyCollection createMyCollection(MyCollectionReqDTO myCollectionReqDTO, int memberId) throws IOException {

        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId)
                .orElseThrow(IllegalArgumentException::new);

        String imagePath = "";
        if (myCollectionReqDTO.getFile().getSize() != 0){
            try { imagePath = imageUtil.uploadImage(myCollectionReqDTO.getFile(), "my-collection"); }
            catch(IOException e) { throw new IOException(e);}
            catch(IllegalStateException e) { throw new IllegalStateException(e);}
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

    @Override
    public MyCollection editMyCollection(MyCollectionReqDTO myCollectionReqDTO, String collectionUuid, int memberId) throws IOException {

        MyCollection myCollection = myCollectionRepository.findByUuidAndExpiredIsFalse(UUID.fromString(collectionUuid))
                .orElseThrow(IllegalArgumentException::new);
        String imagePath = myCollection.getImagePath();

        if (myCollection.getMember().getId() != memberId) {
            throw new BadRequestException("수정 권한이 없습니다.");
        }

        if (myCollectionReqDTO.getFile().getSize() != 0){
            if (!imagePath.isEmpty()){
                try {
                    imageUtil.removeImage(imagePath);
                    imagePath = "";
                    myCollection.setImagePath(imagePath);
                }
                catch (SecurityException e) {throw new SecurityException(e.getMessage());}
                catch (NullPointerException e) { throw new NullPointerException(e.getMessage()); }
            }

            try {
                imagePath = imageUtil.uploadImage(myCollectionReqDTO.getFile(), "my-collection");
            }
            catch(IOException e) { throw new IOException(e);}
            catch(IllegalStateException e) { throw new IllegalStateException(e);}
        }

        myCollection.setImagePath(imagePath);
        if (!myCollectionReqDTO.getDate().isEmpty()) myCollection.setDate(myCollectionReqDTO.getDate());
        if (!myCollectionReqDTO.getCampsiteName().isEmpty()) myCollection.setCampsiteName(myCollectionReqDTO.getCampsiteName());
        if (!myCollectionReqDTO.getContent().isEmpty()) myCollection.setContent(myCollectionReqDTO.getContent());

        return myCollectionRepository.save(myCollection);
    }

    @Override
    public List<MyCollection> getLatestMyCollections(int memberId) {

        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId)
                .orElseThrow(IllegalArgumentException::new);

        List<MyCollection> myCollections = myCollectionRepository.findTop5ByMemberAndExpiredIsFalseOrderByUpdatedAtDesc(member);

        if (myCollections.isEmpty()) return new ArrayList<>();
        return myCollections;
    }

    @Override
    public List<MyCollection> getMyCollections(int memberId) {

        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId)
                .orElseThrow(IllegalArgumentException::new);

        List<MyCollection> myCollections = myCollectionRepository.findByMemberAndExpiredIsFalse(member);

        if (myCollections.isEmpty()) return new ArrayList<>();
        return myCollections;
    }

    @Override
    public MyCollection getMyCollection(String collectionUuid) {

        return myCollectionRepository.findByUuidAndExpiredIsFalse(UUID.fromString(collectionUuid))
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public void deleteMyCollection(String collectionUuid, int memberId) {
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
        catch (SecurityException e) {throw new SecurityException(e.getMessage());}
        catch (NullPointerException e) {throw new NullPointerException(e.getMessage());}
        myCollectionRepository.deleteById(myCollection.getId());
    }
}
