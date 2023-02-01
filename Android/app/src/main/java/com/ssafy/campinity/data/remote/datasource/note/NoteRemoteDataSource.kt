package com.ssafy.campinity.data.remote.datasource.note

interface NoteRemoteDataSource {

    suspend fun noteQuestionRequest(campsiteId: String): List<NoteQuestionResponse>

    suspend fun noteMyQuestionRequest(campsiteId: String): List<NoteQuestionResponse>

    suspend fun getPostQuestionRequest(
        campsiteId: String,
        content: String
    ): NoteQuestionResponse

    suspend fun noteQuestionDetailRequest(questionId: String): NoteDetailResponse

    suspend fun getPostAnswerRequest(
        noteQuestionAnswerRequest: NoteQuestionAnswerRequest
    ): NoteQuestionAnswerResponse
}