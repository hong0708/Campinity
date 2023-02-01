package com.ssafy.campinity.data.remote.service

import com.ssafy.campinity.data.remote.datasource.note.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NoteApiService {

    @GET("/api/v6/questions/lists/{campsiteId}")
    suspend fun getNoteQuestion(@Path("campsiteId") uuid: String): List<NoteQuestionResponse>

    @GET("/api/v6/questions/lists/{campsiteId}/members")
    suspend fun getNoteMyQuestion(@Path("campsiteId") uuid: String): List<NoteQuestionResponse>

    @POST("/api/v6/questions")
    suspend fun createQuestion(
        @Body body: NoteQuestionRequest
    ): NoteQuestionResponse

    @GET("/api/v6/questions/{questionId}")
    suspend fun getNoteQuestionDetail(@Path("questionId") uuid: String): NoteDetailResponse

    @POST("/api/v6/answers")
    suspend fun createAnswer(@Body body: NoteQuestionAnswerRequest): NoteQuestionAnswerResponse
}