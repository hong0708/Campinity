package com.ssafy.campinity.data.remote.repository

import com.ssafy.campinity.common.util.wrapToResource
import com.ssafy.campinity.data.local.AreaDataBase
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.search.AreaEntity
import com.ssafy.campinity.domain.entity.search.GugunItem
import com.ssafy.campinity.domain.entity.search.SidoItem
import com.ssafy.campinity.domain.repository.AreaRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class AreaRepositoryImpl @Inject constructor(
    private val appDataBase: AreaDataBase
) : AreaRepository {

    override suspend fun insert(areaEntity: AreaEntity) {
        appDataBase.areaDao().insert(areaEntity)
    }

    override suspend fun getSidoAll(): Resource<List<SidoItem>> = wrapToResource(Dispatchers.IO) {
        appDataBase.areaDao().getSidoAll().map { it.toDomainModel() }
    }

    override suspend fun getGugun(sidoName: String): Resource<List<GugunItem>> =
        wrapToResource(Dispatchers.IO) {
            appDataBase.areaDao().getGugun(sidoName).map { it.toDomainModel() }
        }
}