package com.ssafy.campinity.data.remote.datasource.user

import com.google.gson.annotations.SerializedName
import com.ssafy.campinity.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.campinity.domain.entity.user.User

data class UserResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("nickName")
    val nickName: String,
    @SerializedName("profileImg")
    val profileImg: String?
) : DataToDomainMapper<User> {
    override fun toDomainModel(): User {
        return User(
            email, nickName, profileImg
        )
    }
}