package com.ssafy.campinity.core.service;


import com.ssafy.campinity.core.dto.MyCollectionReqDTO;
import com.ssafy.campinity.core.entity.MyCollection.MyCollection;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public interface MyCollectionService {

    MyCollection createMyCollection(MyCollectionReqDTO myCollectionReqDTO, String MemberUuid);

    List<MyCollection> getMyCollections(String MemberUuid);

    MyCollection getMyCollection(String collectionUuid);

    MyCollection editMyCollection(MyCollectionReqDTO myCollectionReqDTO, String collectionUuid) throws UnsupportedEncodingException, FileNotFoundException;

    void deleteMyCollection(String collectionUuid) throws FileNotFoundException;

}
