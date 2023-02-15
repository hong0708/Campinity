package com.ssafy.campinity.data.remote.service

import com.ssafy.campinity.data.remote.datasource.chat.RoomResponse
import retrofit2.http.GET

interface ChatApiService {

    @GET("/api/v10/chat/rooms")
    suspend fun getRooms(): List<RoomResponse>
}