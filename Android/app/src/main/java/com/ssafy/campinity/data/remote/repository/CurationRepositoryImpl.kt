package com.ssafy.campinity.data.remote.repository

import com.ssafy.campinity.common.util.wrapToResource
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.curation.CurationRemoteDataSource
import com.ssafy.campinity.domain.entity.curation.CurationDetailItem
import com.ssafy.campinity.domain.entity.curation.CurationItem
import com.ssafy.campinity.domain.repository.CurationRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class CurationRepositoryImpl @Inject constructor(
    private val curationRemoteDataSource: CurationRemoteDataSource
) : CurationRepository {

    override suspend fun getCurations(curationCategory: String): Resource<List<CurationItem>> =
        wrapToResource(Dispatchers.IO) {
            curationRemoteDataSource.getCurations(curationCategory).map { it.toDomainModel() }
        }

    override suspend fun getCuration(curationId: String): Resource<CurationDetailItem> =
        wrapToResource(Dispatchers.IO) {
            curationRemoteDataSource.getCuration(curationId).toDomainModel()
        }
}