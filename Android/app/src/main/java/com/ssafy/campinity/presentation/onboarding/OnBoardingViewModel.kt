package com.ssafy.campinity.presentation.onboarding

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.campinity.ApplicationClass
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.auth.AuthRequest
import com.ssafy.campinity.domain.entity.auth.Token
import com.ssafy.campinity.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _accessToken = MutableLiveData<String?>()
    val accessToken = _accessToken

    fun requestLogin(body: AuthRequest) = viewModelScope.launch {
        when (val value = loginUseCase(body)) {
            is Resource.Success<Token> -> {
                val token = value.data.accessToken
                ApplicationClass.preferences.accessToken = token
                _accessToken.value = token
            }
            is Resource.Error -> {
                Log.d("requestLogin", "requestLogin: ${value.errorMessage}")
                _accessToken.value = ""
            }
        }
    }
}