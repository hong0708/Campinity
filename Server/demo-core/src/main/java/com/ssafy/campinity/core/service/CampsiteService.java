package com.ssafy.campinity.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.campinity.core.dto.*;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface CampsiteService {
    List<Campsite> getMetaDataListByLatLng(LocationInfoDTO locationInfoDTO);
    List<CampsiteListResDTO> getCampsitesByLatLng(LocationInfoDTO locationInfoDTO, int memberId);

    List<ClusteringDoLevelResDTO> getScopeClusteringByDoLevel(LocationInfoDTO locationInfoDTO, int memberId);

    List<ClusteringSigunguLevelResDTO> getScopeClusteringBySigunguLevel(LocationInfoDTO locationInfoDTO, int memberId);

    CampsitePagingResDTO getScopeClusteringByDetailLevel(LocationInfoDTO locationInfoDTO, int memberId, int paging);

    List<CampsiteListResDTO> getCampsiteListByFiltering(String keyword, String doName, String[] sigunguNames,
                                                        String[] fclties, String[] amenities, String[] industries,
                                                        String[] themes, String[] allowAnimals, String[] openSeasons, int memberId);

    List<ClusteringDoLevelResDTO> getCampsiteClusteringByDoLevel(String keyword, String doName, String[] sigunguNames,
                                                        String[] fclties, String[] amenities, String[] industries,
                                                        String[] themes, String[] allowAnimals, String[] openSeasons, int memberId);

    List<ClusteringSigunguLevelResDTO> getCampsiteClusteringBySigunguLevel(String keyword, String doName, String[] sigunguNames,
                                                        String[] fclties, String[] amenities, String[] industries,
                                                        String[] themes, String[] allowAnimals, String[] openSeasons, int memberId);

    CampsitePagingResDTO getCampsiteClusteringByDetailLevel(String keyword, String doName, String[] sigunguNames,
                                                        String[] fclties, String[] amenities, String[] industries,
                                                        String[] themes, String[] allowAnimals, String[] openSeasons, int memberId, int paging);

    CampsiteListResDTO getCampsiteMetaData(UUID campsiteId, int memberId);

    Boolean scrap(int userId, UUID campsiteId);

    CampsiteDetailResDTO getCampsiteDetail(UUID campsiteId, int userId);

    List<CampsiteMetaResDTO> getCampsiteScrapList(int memberId);

    List<Campsite> getCampsiteByCampName(String keyword);

    List<CampsiteRankingResDTO> getHottestCampsite() throws JsonProcessingException;

    List<CampsiteRankingResDTO> getHighestCampsite() throws JsonProcessingException;
}
