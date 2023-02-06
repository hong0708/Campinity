package com.ssafy.campinity.presentation.join

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.campinity.ApplicationClass
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.service.FirebaseService
import com.ssafy.campinity.domain.entity.user.User
import com.ssafy.campinity.domain.usecase.user.CheckDuplicationUseCase
import com.ssafy.campinity.domain.usecase.user.EditUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class JoinViewModel @Inject constructor(
    private val editUserUseCase: EditUserUseCase,
    private val checkDuplicationUseCase: CheckDuplicationUseCase
) : ViewModel() {

    private val _isDuplicate: MutableLiveData<Boolean?> = MutableLiveData(null)
    val isDuplicate: MutableLiveData<Boolean?> = _isDuplicate

    private val _isSuccess: MutableLiveData<Boolean?> = MutableLiveData(false)
    val isSuccess: MutableLiveData<Boolean?> = _isSuccess

    private val _nickname: MutableLiveData<String> = MutableLiveData(null)
    val nickname: MutableLiveData<String> = _nickname

    private val _profileImgUri: MutableLiveData<Uri?> = MutableLiveData(null)
    val profileImgUri: LiveData<Uri?> = _profileImgUri

    private var profileImgMultiPart: MultipartBody.Part? = null

    val fcmToken: MutableLiveData<String> = MutableLiveData()

    fun requestCurrentToken() = viewModelScope.launch {
        val result = FirebaseService().getCurrentToken()
        ApplicationClass.preferences.fcmToken = result
    }

    fun setNickname(nickname: String) {
        viewModelScope.launch {
            _nickname.value = nickname
        }
    }

    fun setProfileImg(uri: Uri, file: File) {
        viewModelScope.launch {
            _profileImgUri.value = uri
            val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            profileImgMultiPart =
                MultipartBody.Part.createFormData("profileImg", file.name, requestFile)
        }
    }

    fun updateProfile(fcmToken: String) {
        viewModelScope.launch {
            when (val value =
                editUserUseCase.editUserInfo(
                    requireNotNull(nickname.value),
                    profileImgMultiPart,
                    fcmToken
                )) {
                is Resource.Success<User> -> {
                    ApplicationClass.preferences.isLoggedIn = true
                    _isSuccess.value = true
                }
                is Resource.Error -> {
                    Log.e("updateprofile", "updateProfile: ${value.errorMessage}")

                }
            }
        }
    }

    fun updateProfileWithoutImg(fcmToken: String) {
        viewModelScope.launch {
            when (val value =
                editUserUseCase.editUserInfoWithoutimg(
                    requireNotNull(nickname.value),
                    fcmToken
                )) {
                is Resource.Success<User> -> {
                    ApplicationClass.preferences.isLoggedIn = true
                    _isSuccess.value = true
                }
                is Resource.Error -> {
                    Log.e("updateprofile", "updateProfile: ${value.errorMessage}")

                }
            }
        }
    }

    fun checkDuplication() {
        viewModelScope.launch {
            when (val value = checkDuplicationUseCase.checkDuplication(nickname.value!!)) {
                is Resource.Success<Boolean> -> {
                    _isDuplicate.value = value.data
                }
                is Resource.Error -> {}
            }
        }
    }
}