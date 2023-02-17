package com.ssafy.campinity.data.remote.datasource.note

import com.google.gson.annotations.SerializedName
import com.ssafy.campinity.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.campinity.domain.entity.community.NoteDetail
import com.ssafy.campinity.domain.entity.community.NoteQuestionAnswer

data class NoteDetailResponse(
    @SerializedName("content")
    val content: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("questionId")
    val questionId: String,
    val answers: List<NoteQuestionAnswer>
) : DataToDomainMapper<NoteDetail> {
    override fun toDomainModel(): NoteDetail {
        return NoteDetail(
            answers,
            content,
            createdAt,
            questionId
        )
    }
}