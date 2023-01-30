package com.ssafy.campinity.data.remote.datasource.note

import com.google.gson.annotations.SerializedName

data class NoteQuestionRequest(
    @SerializedName("content")
    val content: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("questionId")
    val questionId: String
)
