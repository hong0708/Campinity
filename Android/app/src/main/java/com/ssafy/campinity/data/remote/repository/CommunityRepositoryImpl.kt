package com.ssafy.campinity.data.remote.repository

import com.ssafy.campinity.common.util.wrapToResource
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.communitycampsite.CommunityRemoteDataSource
import com.ssafy.campinity.domain.entity.community.CampsiteBriefInfo
import com.ssafy.campinity.domain.entity.community.CampsiteMessageBriefInfo
import com.ssafy.campinity.domain.repository.CommunityRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(
    private val communityRemoteDataSource: CommunityRemoteDataSource
) : CommunityRepository {

    override suspend fun getCampsiteBriefInfoByCampName(campsiteName: String)
            : Resource<List<CampsiteBriefInfo>> =
        wrapToResource(Dispatchers.IO) {
            communityRemoteDataSource.getCampsiteBriefInfoByCampName(campsiteName)
                .map { it.toDomainModel() }
        }

    override suspend fun getCampsiteBriefInfoByUserLocation(
        bottomRightLat: Double,
        bottomRightLng: Double,
        topLeftLat: Double,
        topLeftLng: Double
    ): Resource<List<CampsiteBriefInfo>> =
        wrapToResource(Dispatchers.IO) {
            communityRemoteDataSource.getCampsiteBriefInfoByUserLocation(
                bottomRightLat,
                bottomRightLng,
                topLeftLat,
                topLeftLng
            ).map { it.toDomainModel() }
        }

    override suspend fun getCampsiteMessageBriefInfoBYScope(
        bottomRightLat: Double,
        bottomRightLng: Double,
        campsiteName: String,
        topLeftLat: Double,
        topLeftLng: Double
    ): Resource<List<CampsiteMessageBriefInfo>> =
        wrapToResource(Dispatchers.IO) {
            communityRemoteDataSource.getCampsiteMessagesBriefInfoByScope(
                bottomRightLat,
                bottomRightLng,
                campsiteName,
                topLeftLat,
                topLeftLng
            ).map { it.toDomainModel() }
        }
}