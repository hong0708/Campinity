package com.ssafy.campinity.data.remote.repository

import com.ssafy.campinity.common.util.wrapToResource
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.note.NoteQuestionAnswerRequest
import com.ssafy.campinity.data.remote.datasource.note.NoteRemoteDataSource
import com.ssafy.campinity.domain.entity.community.NoteDetail
import com.ssafy.campinity.domain.entity.community.NoteQuestionAnswer
import com.ssafy.campinity.domain.entity.community.NoteQuestionTitle
import com.ssafy.campinity.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteRemoteDataSource: NoteRemoteDataSource
) : NoteRepository {

    override suspend fun getQuestions(campsiteId: String): Resource<List<NoteQuestionTitle>> =
        wrapToResource(Dispatchers.IO) {
            noteRemoteDataSource.noteQuestionRequest(campsiteId).map { it.toDomainModel() }
        }

    override suspend fun getMyQuestions(campsiteId: String): Resource<List<NoteQuestionTitle>> =
        wrapToResource(Dispatchers.IO) {
            noteRemoteDataSource.noteMyQuestionRequest(campsiteId).map { it.toDomainModel() }
        }

    override suspend fun getPostQuestion(
        campsiteId: String,
        content: String
    ): Resource<NoteQuestionTitle> =
        wrapToResource(Dispatchers.IO) {
            noteRemoteDataSource.getPostQuestionRequest(campsiteId, content).toDomainModel()
        }

    override suspend fun getQuestionsDetail(questionId: String): Resource<NoteDetail> =
        wrapToResource(Dispatchers.IO) {
            noteRemoteDataSource.noteQuestionDetailRequest(questionId).toDomainModel()
        }

    override suspend fun getPostAnswer(
        noteQuestionAnswerRequest: NoteQuestionAnswerRequest
    ): Resource<NoteQuestionAnswer> =
        wrapToResource(Dispatchers.IO) {
            noteRemoteDataSource.getPostAnswerRequest(noteQuestionAnswerRequest).toDomainModel()
        }
}