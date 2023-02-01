package com.ssafy.campinity.data.remote.datasource.note

import com.ssafy.campinity.data.remote.service.NoteApiService
import javax.inject.Inject

class NoteRemoteDataSourceImpl @Inject constructor(
    private val noteApiService: NoteApiService
) : NoteRemoteDataSource {

    override suspend fun noteQuestionRequest(campsiteId: String): List<NoteQuestionResponse> =
        noteApiService.noteQuestionRequest(campsiteId)

    override suspend fun noteMyQuestionRequest(campsiteId: String): List<NoteQuestionResponse> =
        noteApiService.noteMyQuestionRequest(campsiteId)

    override suspend fun getPostQuestionRequest(
        campsiteId: String,
        content: String
    ): NoteQuestionResponse =
        noteApiService.postQuestionRequest(campsiteId, content)

    override suspend fun noteQuestionDetailRequest(questionId: String): NoteDetailResponse =
        noteApiService.noteQuestionDetailRequest(questionId)

    override suspend fun getPostAnswerRequest(
        noteQuestionAnswerRequest: NoteQuestionAnswerRequest
    ): NoteQuestionAnswerResponse {
        return noteApiService.postAnswerRequest(noteQuestionAnswerRequest)
    }
}