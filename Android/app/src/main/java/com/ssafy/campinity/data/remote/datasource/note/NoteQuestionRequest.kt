package com.ssafy.campinity.data.remote.datasource.note

import com.google.gson.annotations.SerializedName

data class NoteQuestionRequest(
    @SerializedName("campsiteId")
    val campsiteId: String,
    @SerializedName("content")
    val content: String
)
