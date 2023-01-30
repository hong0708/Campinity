package com.ssafy.campinity.data.remote.datasource.note


interface NoteRemoteDataSource {

    suspend fun noteQuestionRequest(campsiteId: String): List<NoteQuestionResponse>

    suspend fun noteMyQuestionRequest(campsiteId: String): List<NoteQuestionResponse>
}