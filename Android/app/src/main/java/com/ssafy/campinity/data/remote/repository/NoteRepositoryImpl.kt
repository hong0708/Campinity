package com.ssafy.campinity.data.remote.repository

import com.ssafy.campinity.data.remote.datasource.note.NoteRemoteDataSource
import com.ssafy.campinity.domain.repository.NoteRepository
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteRemoteDataSource: NoteRemoteDataSource
) : NoteRepository {
    /*override suspend fun noteQuestionRequest(body: AuthRequest): Resource<Token> =
        wrapToResource(Dispatchers.IO) {
            authRemoteDataSource.loginRequest(body.accessToken).toDomainModel()
        }*/
}