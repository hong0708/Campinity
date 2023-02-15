package com.ssafy.campinity.presentation.community.campsite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.fcm.FCMReplyRequest
import com.ssafy.campinity.domain.usecase.fcm.RequestReplyHelpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiveHelpNoteViewModel @Inject constructor(
    private val requestReplyHelpUseCase: RequestReplyHelpUseCase
) : ViewModel() {

    private val _assigned: MutableLiveData<Boolean?> = MutableLiveData()
    val assigned: LiveData<Boolean?> = _assigned

    fun replyHelp(fcmMessageId: String, fcmToken: String) = viewModelScope.launch {
        when (val value = requestReplyHelpUseCase(FCMReplyRequest(fcmMessageId, fcmToken))) {
            is Resource.Success<Int?> -> {
                if (value.data == 0) {
                    _assigned.value = false
                } else {
                    _assigned.value = true
                    Log.d("replyHelp", "replyHelp: ${value.data}")
                }
            }
            is Resource.Error -> {
                Log.e("replyHelp", "${value.errorMessage}")
            }
        }
    }
}