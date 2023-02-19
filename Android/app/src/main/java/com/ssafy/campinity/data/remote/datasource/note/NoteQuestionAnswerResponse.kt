package com.ssafy.campinity.data.remote.datasource.note

import com.google.gson.annotations.SerializedName
import com.ssafy.campinity.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.campinity.domain.entity.community.NoteQuestionAnswer

data class NoteQuestionAnswerResponse(
    @SerializedName("answerId")
    val answerId: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("createdAt")
    val createdAt: String,
) : DataToDomainMapper<NoteQuestionAnswer> {
    override fun toDomainModel(): NoteQuestionAnswer {
        return NoteQuestionAnswer(
            answerId,
            content,
            createdAt,
        )
    }
}