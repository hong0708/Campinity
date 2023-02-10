package com.ssafy.campinity.presentation.splash

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.campinity.ApplicationClass
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.service.FirebaseService
import com.ssafy.campinity.domain.entity.fcm.FCMToken
import com.ssafy.campinity.domain.entity.mypage.MyPageUser
import com.ssafy.campinity.domain.usecase.fcm.RequestRenewTokenUseCase
import com.ssafy.campinity.domain.usecase.mypage.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val requestRenewTokenUseCase: RequestRenewTokenUseCase
) : ViewModel() {

    private val _nickname: MutableLiveData<String> = MutableLiveData()
    val nickname: MutableLiveData<String> = _nickname

    fun getInfo() = viewModelScope.launch {
        when (val value = getUserInfoUseCase()) {
            is Resource.Success<MyPageUser> -> {
                _nickname.value = value.data.name
            }
            is Resource.Error -> {
                Log.e("getUserInfo", "getUserInfo: ${value.errorMessage}")
            }
        }
    }

    fun renewToken() = viewModelScope.launch {
        when (val value = requestRenewTokenUseCase(FirebaseService().getCurrentToken())) {
            is Resource.Success<FCMToken> -> {
                ApplicationClass.preferences.fcmToken = value.data.token
            }
            is Resource.Error -> {
                Log.e("renewToken", "renewToken: ${value.errorMessage}")
            }
        }
    }
}