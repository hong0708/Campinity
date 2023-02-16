package com.ssafy.campinity.presentation.onboarding

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import com.google.gson.Gson
import com.ssafy.campinity.ApplicationClass
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.auth.AuthRequest
import com.ssafy.campinity.domain.entity.auth.Sub
import com.ssafy.campinity.domain.entity.auth.Token
import com.ssafy.campinity.domain.usecase.auth.GetNewTokenUseCase
import com.ssafy.campinity.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val getNewTokenUseCase: GetNewTokenUseCase
) : ViewModel() {

    private val _accessToken = MutableLiveData<String?>()

    private val _refreshToken = MutableLiveData<String?>()
    val refreshToken = _refreshToken

    private val _isExist = MutableLiveData<Boolean?>()
    val isExist = _isExist

    fun requestLogin(body: AuthRequest) = viewModelScope.launch {
        when (val value = loginUseCase(body)) {
            is Resource.Success<Token> -> {
                val accessToken = value.data.accessToken
                val refreshToken = value.data.refreshToken

                ApplicationClass.preferences.accessToken = accessToken
                ApplicationClass.preferences.refreshToken = refreshToken

                _accessToken.value = accessToken
                _refreshToken.value = refreshToken
                _isExist.value = value.data.isExist
                ApplicationClass.preferences.isLoggedIn = true

                getUuidFromJwt(value.data.accessToken)
            }
            is Resource.Error -> {
                Log.d("requestLogin", "requestLogin: ${value.errorMessage}")
                if (value.errorMessage == "401") {
                    getNewToken()
                }
            }
        }
    }

    private fun getNewToken() = viewModelScope.launch {
        when (val value = getNewTokenUseCase()) {
            is Resource.Success -> {
                val accessToken = value.data.accessToken
                ApplicationClass.preferences.accessToken = accessToken
                _accessToken.value = accessToken
                _refreshToken.value = ""
            }
            is Resource.Error -> {
                Log.d("getNewToken", "getNewToken: ${value.errorMessage}")
            }
        }
    }

    private fun getUuidFromJwt(jwtToken: String?) {
        if (jwtToken != null) {
            val decodedJwt: DecodedJWT = JWT.decode(jwtToken)
            ApplicationClass.preferences.memberId =
                Gson().fromJson(decodedJwt.getClaim("sub").asString(), Sub::class.java).uuid
        }
    }
}