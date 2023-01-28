package com.ssafy.campinity.data.remote.datasource.note

import com.ssafy.campinity.data.remote.datasource.auth.AuthResponse

interface NoteRemoteDataSource {

    suspend fun noteQuestionRequest(campsiteId: String): AuthResponse
}