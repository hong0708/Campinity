package com.ssafy.campinity.data.remote.datasource.chat

interface ChatRemoteDataSource {

    suspend fun getRooms(): List<RoomResponse>
}