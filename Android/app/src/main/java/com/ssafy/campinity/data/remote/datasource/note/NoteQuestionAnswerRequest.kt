package com.ssafy.campinity.data.remote.datasource.note

import com.google.gson.annotations.SerializedName

data class NoteQuestionAnswerRequest(
    @SerializedName("content")
    val content: String,
    @SerializedName("questionId")
    val questionId: String,
)
