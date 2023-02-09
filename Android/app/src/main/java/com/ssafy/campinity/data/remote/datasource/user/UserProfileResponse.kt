package com.ssafy.campinity.data.remote.datasource.user

import com.google.gson.annotations.SerializedName
import com.ssafy.campinity.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.campinity.domain.entity.user.UserProfile

data class UserProfileResponse(
    @SerializedName("profile")
    val profileImg: String?
) : DataToDomainMapper<UserProfile> {
    override fun toDomainModel(): UserProfile = UserProfile(profileImg)
}
