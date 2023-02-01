package com.ssafy.campinity.data.remote.datasource.note

interface NoteRemoteDataSource {

    suspend fun getNoteQuestion(campsiteId: String): List<NoteQuestionResponse>

    suspend fun getNoteMyQuestion(campsiteId: String): List<NoteQuestionResponse>

    suspend fun createNoteQuestion(
        noteQuestionRequest: NoteQuestionRequest
    ): NoteQuestionResponse

    suspend fun getNoteQuestionDetail(questionId: String): NoteDetailResponse

    suspend fun createQuestionAnswer(
        noteQuestionAnswerRequest: NoteQuestionAnswerRequest
    ): NoteQuestionAnswerResponse
}