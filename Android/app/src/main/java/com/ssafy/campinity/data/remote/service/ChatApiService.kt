package com.ssafy.campinity.data.remote.service

import com.ssafy.campinity.data.remote.datasource.chat.MessageResponse
import com.ssafy.campinity.data.remote.datasource.chat.RoomResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ChatApiService {

    @GET("/api/v10/chat/rooms")
    suspend fun getRooms(): List<RoomResponse>

    @GET("/api/v10/chat/{roomId}/messages")
    suspend fun getMessages(@Path("roomId") roomId: String): MessageResponse
}