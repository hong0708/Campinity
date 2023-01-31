package com.ssafy.campinity.data.remote.service

import com.ssafy.campinity.data.remote.datasource.note.NoteDetailResponse
import com.ssafy.campinity.data.remote.datasource.note.NoteQuestionResponse
import retrofit2.http.*

interface NoteApiService {

    @GET("/api/v6/questions/lists/{campsiteId}")
    suspend fun noteQuestionRequest(@Path("campsiteId") uuid: String): List<NoteQuestionResponse>

    @GET("/api/v6/questions/lists/{campsiteId}/members")
    suspend fun noteMyQuestionRequest(@Path("campsiteId") uuid: String): List<NoteQuestionResponse>

    @POST("/api/v6/questions")
    suspend fun postQuestionRequest(
        @Query("campsiteId") campsiteId: String,
        @Query("content") content: String
    ): NoteQuestionResponse

    @GET("/api/v6/questions/{questionId}")
    suspend fun noteQuestionDetailRequest(@Path("questionId") uuid: String): NoteDetailResponse
}