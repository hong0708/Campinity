package com.ssafy.campinity.domain.repository

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.search.CampsiteBriefInfo

interface SearchRepository {

    suspend fun getCampsitesByArea(sido: String, gugun: String): Resource<List<CampsiteBriefInfo>>
}