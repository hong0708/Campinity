package com.ssafy.campinity.data.remote.datasource.note

import com.ssafy.campinity.data.remote.service.NoteApiService
import javax.inject.Inject

class NoteRemoteDataSourceImpl @Inject constructor(
    private val noteApiService: NoteApiService
) : NoteRemoteDataSource {

    override suspend fun getNoteQuestion(campsiteId: String): List<NoteQuestionResponse> =
        noteApiService.getNoteQuestion(campsiteId)

    override suspend fun getNoteMyQuestion(campsiteId: String): List<NoteQuestionResponse> =
        noteApiService.getNoteMyQuestion(campsiteId)

    override suspend fun createNoteQuestion(
        noteQuestionRequest: NoteQuestionRequest
    ): NoteQuestionResponse =
        noteApiService.createQuestion(noteQuestionRequest)

    override suspend fun getNoteQuestionDetail(questionId: String): NoteDetailResponse =
        noteApiService.getNoteQuestionDetail(questionId)

    override suspend fun createQuestionAnswer(
        noteQuestionAnswerRequest: NoteQuestionAnswerRequest
    ): NoteQuestionAnswerResponse {
        return noteApiService.createAnswer(noteQuestionAnswerRequest)
    }
}