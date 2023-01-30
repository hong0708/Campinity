package com.ssafy.campinity.data.remote.datasource.note

import com.ssafy.campinity.data.remote.datasource.auth.AuthRemoteDataSource
import com.ssafy.campinity.data.remote.datasource.auth.AuthResponse
import com.ssafy.campinity.data.remote.service.AuthApiService
import com.ssafy.campinity.data.remote.service.NoteApiService
import javax.inject.Inject

class NoteRemoteDataSourceImpl @Inject constructor(
    private val noteApiService: NoteApiService
) : NoteRemoteDataSource {
    /*override suspend fun loginRequest(code: String): AuthResponse {
        return authApiService.loginRequest(code)
    }*/
    override suspend fun noteQuestionRequest(campsiteId: String): List<NoteQuestionResponse> {
        return noteApiService.noteQuestionRequest(campsiteId)
    }

    override suspend fun noteMyQuestionRequest(campsiteId: String): List<NoteQuestionResponse> {
        return noteApiService.noteQuestionRequest(campsiteId)
    }
}