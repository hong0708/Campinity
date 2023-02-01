package com.ssafy.campinity.data.remote.repository

import com.ssafy.campinity.common.util.wrapToResource
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.note.NoteQuestionAnswerRequest
import com.ssafy.campinity.data.remote.datasource.note.NoteQuestionRequest
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
            noteRemoteDataSource.getNoteQuestion(campsiteId).map { it.toDomainModel() }
        }

    override suspend fun getMyQuestions(campsiteId: String): Resource<List<NoteQuestionTitle>> =
        wrapToResource(Dispatchers.IO) {
            noteRemoteDataSource.getNoteMyQuestion(campsiteId).map { it.toDomainModel() }
        }

    override suspend fun createQuestion(
        noteQuestionRequest: NoteQuestionRequest
    ): Resource<NoteQuestionTitle> =
        wrapToResource(Dispatchers.IO) {
            noteRemoteDataSource.createNoteQuestion(noteQuestionRequest).toDomainModel()
        }

    override suspend fun getQuestionsDetail(questionId: String): Resource<NoteDetail> =
        wrapToResource(Dispatchers.IO) {
            noteRemoteDataSource.getNoteQuestionDetail(questionId).toDomainModel()
        }

    override suspend fun createAnswer(
        noteQuestionAnswerRequest: NoteQuestionAnswerRequest
    ): Resource<NoteQuestionAnswer> =
        wrapToResource(Dispatchers.IO) {
            noteRemoteDataSource.createQuestionAnswer(noteQuestionAnswerRequest).toDomainModel()
        }
}