package com.ssafy.campinity.presentation.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.chat.ChatItem
import com.ssafy.campinity.domain.usecase.chat.GetMessagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val getMessagesUseCase: GetMessagesUseCase
) : ViewModel() {

    private val _chatMessages: MutableLiveData<List<ChatItem>?> = MutableLiveData()
    val chatMessages: LiveData<List<ChatItem>?> = _chatMessages

    fun getMessages(roomId: String) = viewModelScope.launch {
        when (val value = getMessagesUseCase(roomId)) {
            is Resource.Success<List<ChatItem>> -> {
                _chatMessages.value = value.data
            }
            is Resource.Error -> {
                Log.e("getMessages", "getMessages: ${value.errorMessage}")
            }
        }
    }
}