package com.ssafy.campinity.core.service;

import com.ssafy.campinity.core.dto.*;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface CampsiteService {
    List<Campsite> getCampsitesByLatLng(LocationInfoDTO locationInfoDTO);

    List<CampsiteListResDTO> getCampsiteListByFiltering(String keyword, String doName, String sigunguName,
                                                        String[] fclties, String[] amenities, String[] industries,
                                                        String[] themes, String[] allowAnimals, String[] openSeasons, int memberId);

    CampsiteListResDTO getCampsiteMetaData(UUID campsiteId, int memberId);

    Boolean scrap(int userId, UUID campsiteId);

    CampsiteDetailResDTO getCampsiteDetail(UUID campsiteId, int userId);
}
