package com.ssafy.campinity.presentation.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.chat.ChatItem
import com.ssafy.campinity.domain.entity.chat.RoomItem
import com.ssafy.campinity.domain.usecase.chat.GetRoomsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getRoomsUseCase: GetRoomsUseCase
) : ViewModel() {

    private val _chatRoomsData: MutableLiveData<List<RoomItem>?> = MutableLiveData()
    val chatRoomsData: LiveData<List<RoomItem>?> = _chatRoomsData

    private val _chatMessages: MutableLiveData<List<ChatItem>?> = MutableLiveData()
    val chatMessages: LiveData<List<ChatItem>?> = _chatMessages

    fun getRooms() = viewModelScope.launch {
        when (val value = getRoomsUseCase()) {
            is Resource.Success<List<RoomItem>> -> {
                _chatRoomsData.value = value.data
            }
            is Resource.Error -> {
                Log.e("getRooms", "getRooms: ${value.errorMessage}")
            }
        }
    }
}