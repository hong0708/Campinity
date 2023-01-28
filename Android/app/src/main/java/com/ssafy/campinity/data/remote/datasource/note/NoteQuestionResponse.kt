package com.ssafy.campinity.data.remote.datasource.note

import com.google.gson.annotations.SerializedName
import com.ssafy.campinity.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.campinity.domain.entity.community.NoteQuestionTitle

data class NoteQuestionResponse(
    @SerializedName("content")
    val content: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("questionId")
    val questionId: String
) : DataToDomainMapper<NoteQuestionTitle> {
    /*override fun toDomainModel(): NoteQuestionTitle {
        return NoteQuestionTitle
    }*/
}