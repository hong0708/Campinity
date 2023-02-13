package com.ssafy.campinity.presentation.community.campsite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.fcm.FCMMessageRequest
import com.ssafy.campinity.data.remote.datasource.fcm.FCMReplyRequest
import com.ssafy.campinity.domain.usecase.fcm.CreateHelpNoteUseCase
import com.ssafy.campinity.domain.usecase.fcm.RequestReplyHelpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityHelpNoteViewModel @Inject constructor(
    private val createHelpNoteUseCase: CreateHelpNoteUseCase,
    private val requestReplyHelpUseCase: RequestReplyHelpUseCase
) : ViewModel() {

    private val _isSucceed: MutableLiveData<Boolean> = MutableLiveData(false)
    val isSucceed: LiveData<Boolean> = _isSucceed

    private val _receiverNum: MutableLiveData<Int?> = MutableLiveData()
    val receiverNum: LiveData<Int?> = _receiverNum

    fun createHelpNoteMessage(
        body: String,
        campsiteId: String,
        hiddenBody: String,
        latitude: Double,
        longitude: Double,
        title: String,
    ) = viewModelScope.launch {
        when (val value = createHelpNoteUseCase(
            FCMMessageRequest(body, campsiteId, hiddenBody, latitude, longitude, title)
        )) {
            is Resource.Success<Int> -> {
                _isSucceed.value = true
                _receiverNum.value = value.data
                Log.d("createHelpNoteMessage", "createHelpNoteMessage: ${_receiverNum.value}")
            }
            is Resource.Error -> {
                Log.d(
                    "createHelpNoteMessage", "createHelpNoteMessage: ${value.errorMessage}"
                )
            }
        }
    }

    fun replyHelp(fcmMessageId: String, fcmToken: String) = viewModelScope.launch {
        when (val value = requestReplyHelpUseCase(FCMReplyRequest(fcmMessageId, fcmToken))) {
            is Resource.Success<Int?> -> {}
            is Resource.Error -> {
                Log.e("replyHelp", "${value.errorMessage}")
            }
        }
    }
}