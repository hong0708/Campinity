package com.ssafy.campinity.data.remote.service

import com.ssafy.campinity.data.remote.datasource.auth.AuthResponse
import com.ssafy.campinity.data.remote.datasource.note.NoteQuestionResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NoteApiService {

    @GET("/api/v6/questions/lists/{campsiteId}")
    suspend fun noteQuestionRequest(@Path("campsiteId") uuid: String): List<NoteQuestionResponse>

    @GET("/api/v6/questions/lists/{campsiteId}/members")
    suspend fun noteMyQuestionRequest(@Path("campsiteId") uuid: String): List<NoteQuestionResponse>
}