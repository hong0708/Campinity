package com.ssafy.campinity.domain.repository

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.search.AreaEntity
import com.ssafy.campinity.domain.entity.search.GugunItem
import com.ssafy.campinity.domain.entity.search.SidoItem

interface AreaRepository {

    suspend fun insert(areaEntity: AreaEntity)

    suspend fun getSidoAll(): Resource<List<SidoItem>>

    suspend fun getGugun(sidoName: String): Resource<List<GugunItem>>
}