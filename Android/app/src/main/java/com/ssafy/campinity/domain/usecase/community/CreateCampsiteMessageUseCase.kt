package com.ssafy.campinity.domain.usecase.community

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.community.CampsiteMessageDetailInfo
import com.ssafy.campinity.domain.repository.CommunityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject

class CreateCampsiteMessageUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(
        messageCategory: String,
        file: MultipartBody.Part?,
        latitude: Double,
        campsiteId: String,
        content: String,
        longitude: Double
    ): Resource<CampsiteMessageDetailInfo> =
        withContext(Dispatchers.IO) {
            communityRepository.createCampsiteMessage(
                messageCategory, file, latitude, campsiteId, content, longitude
            )
        }
}
