package com.ssafy.campinity.data.remote.datasource.mypage

import com.google.gson.annotations.SerializedName
import com.ssafy.campinity.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.campinity.domain.entity.mypage.MyPageUser

data class MyPageUserResponse(
    @SerializedName("imagePath")
    val imagePath: String,
    @SerializedName("name")
    val name: String
) : DataToDomainMapper<MyPageUser> {
    override fun toDomainModel(): MyPageUser {
        return MyPageUser(
            imagePath, name
        )
    }
}
