package com.ssafy.campinity.data.remote.service

import com.ssafy.campinity.data.remote.datasource.note.NoteQuestionRequest
import com.ssafy.campinity.data.remote.datasource.note.NoteQuestionResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NoteApiService {

    @GET("/api/v6/questions/lists/{campsiteId}")
    suspend fun noteQuestionRequest(@Path("campsiteId") uuid: String): List<NoteQuestionResponse>

    @GET("/api/v6/questions/lists/{campsiteId}/members")
    suspend fun noteMyQuestionRequest(@Path("campsiteId") uuid: String): List<NoteQuestionResponse>

    @POST("/api/v6/questions")
    suspend fun postQuestionRequest(@Body body: NoteQuestionRequest): NoteQuestionResponse
}