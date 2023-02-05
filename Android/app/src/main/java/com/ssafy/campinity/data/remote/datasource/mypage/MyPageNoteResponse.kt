package com.ssafy.campinity.data.remote.datasource.mypage

import com.google.gson.annotations.SerializedName
import com.ssafy.campinity.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.campinity.data.remote.datasource.communitycampsite.CommunityCampsiteDetailInfoMessageResponse
import com.ssafy.campinity.domain.entity.mypage.MyPageNote

data class MyPageNoteResponse(
    @SerializedName("myETCMessages")
    val myETCMessages: List<CommunityCampsiteDetailInfoMessageResponse>,
    @SerializedName("myReviewMessages")
    val myReviewMessages: List<CommunityCampsiteDetailInfoMessageResponse>
) : DataToDomainMapper<MyPageNote> {
    override fun toDomainModel(): MyPageNote =
        MyPageNote(
            myETCMessages.map { it.toDomainModel() },
            myReviewMessages.map { it.toDomainModel() }
        )
}