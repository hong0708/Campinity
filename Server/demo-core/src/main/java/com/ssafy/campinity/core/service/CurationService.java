package com.ssafy.campinity.core.service;

import com.ssafy.campinity.core.dto.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public interface CurationService {

    CurationMetaResDTO createCuration(CurationReqDTO curationReqDTO) throws IOException;

    List<CurationMetaResDTO> getCurationBannerList();

    List<CurationMetaResDTO> getCurationList(String curationCategory);

    CurationDetailResDTO getCurationDetail(UUID curationId, int memberId);

    List<CurationScrapListResDTO> getCurationScrapList(int memberId);

    IsScrapResDTO scrapCuration(UUID curationId, int memberId);
}
