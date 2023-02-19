package com.ssafy.campinity.domain.entity.community

data class NoteDetail(
    val answers: List<NoteQuestionAnswer>,
    val content: String,
    val createdAt: String,
    val questionId: String
)
