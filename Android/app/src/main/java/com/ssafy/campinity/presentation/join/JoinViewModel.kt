package com.ssafy.campinity.presentation.join

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val editUserUseCase: EditUserUseCase
) : ViewModel() {

    private val _nickname: MutableLiveData<String> = MutableLiveData(null)
    val nickname: LiveData<String> = _nickname

    private val _profileImgUri: MutableLiveData<Uri?> = MutableLiveData(null)
    val profileImgUri: LiveData<Uri?> = _profileImgUri

    private var profileImgMultiPart: MultipartBody.Part? = null

    fun setNickname(nickname: String) {
        viewModelScope.launch {
            _nickname.value = nickname
        }
    }

    fun setProfileImg(uri: Uri, file: File) {
        viewModelScope.launch {
            _profileImgUri.value = uri
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            profileImgMultiPart = MultipartBody.Part.createFormData("image", file.name, requestFile)
        }
    }

    fun updateProfile() {
        viewModelScope.launch {
            //editUserUseCase.editUserInfo(UserRequest(nickname.value!!, profileImgMultiPart))
        }
    }
}