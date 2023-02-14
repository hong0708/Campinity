package com.ssafy.campinity.domain.repository

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.curation.CurationDetailItem
import com.ssafy.campinity.domain.entity.curation.CurationItem

interface CurationRepository {

    suspend fun getCurations(curationCategory: String): Resource<List<CurationItem>>

    suspend fun getCuration(curationId: String): Resource<CurationDetailItem>

    suspend fun scrapCuration(curationId: String): Resource<Boolean>

    suspend fun getScrapCurations(): Resource<List<CurationItem>>
}