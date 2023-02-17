package com.ssafy.campinity.core.service;


import com.ssafy.campinity.core.dto.MyCollectionReqDTO;
import com.ssafy.campinity.core.entity.MyCollection.MyCollection;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface MyCollectionService {

    MyCollection createMyCollection(MyCollectionReqDTO myCollectionReqDTO, int memberId) throws IOException;

    List<MyCollection> getMyCollections(int memberId);

    List<MyCollection> getLatestMyCollections(int memberId);

    MyCollection getMyCollection(String collectionUuid);

    MyCollection editMyCollection(MyCollectionReqDTO myCollectionReqDTO, String collectionUuid, int memberId) throws IOException;

    void deleteMyCollection(String collectionUuid, int memberId);

}
