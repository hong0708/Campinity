package com.ssafy.campinity.presentation.mypage

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.campinity.ApplicationClass
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.mypage.LogoutRequest
import com.ssafy.campinity.domain.entity.community.CampsiteMessageDetailInfo
import com.ssafy.campinity.domain.entity.mypage.MyPageNote
import com.ssafy.campinity.domain.entity.mypage.MyPageUser
import com.ssafy.campinity.domain.entity.user.User
import com.ssafy.campinity.domain.usecase.community.GetCampsiteMessageDetailInfoUseCase
import com.ssafy.campinity.domain.usecase.mypage.EditUserInfoUseCase
import com.ssafy.campinity.domain.usecase.mypage.GetNotesUseCase
import com.ssafy.campinity.domain.usecase.mypage.GetUserInfoUseCase
import com.ssafy.campinity.domain.usecase.mypage.RequestLogoutUseCase
import com.ssafy.campinity.domain.usecase.user.CheckDuplicationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val getCampsiteMessageDetailInfoUseCase: GetCampsiteMessageDetailInfoUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val checkDuplicationUseCase: CheckDuplicationUseCase,
    private val requestLogoutUseCase: RequestLogoutUseCase,
    private val editUserInfoUseCase: EditUserInfoUseCase
    ) : ViewModel() {

    private val _etcNotesListData: MutableLiveData<List<CampsiteMessageDetailInfo>> = MutableLiveData()
    val etcNotesListData: LiveData<List<CampsiteMessageDetailInfo>?> = _etcNotesListData

    private val _reviewNotesListData: MutableLiveData<List<CampsiteMessageDetailInfo>> = MutableLiveData()
    val reviewNotesListData: LiveData<List<CampsiteMessageDetailInfo>?> = _reviewNotesListData

    private val _detailData: MutableLiveData<CampsiteMessageDetailInfo?> = MutableLiveData()
    val detailData: LiveData<CampsiteMessageDetailInfo?> = _detailData

    private val _isDuplicate: MutableLiveData<Boolean?> = MutableLiveData()
    val isDuplicate: MutableLiveData<Boolean?> = _isDuplicate

    private val _isLoggedOut: MutableLiveData<Boolean?> = MutableLiveData()
    val isLoggedOut: LiveData<Boolean?> = _isLoggedOut

    private val _isSame: MutableLiveData<Boolean?> = MutableLiveData()
    val isSame: MutableLiveData<Boolean?> = _isSame

    private val _nickname: MutableLiveData<String> = MutableLiveData()
    val nickname: MutableLiveData<String> = _nickname

    private val _profileImgUri: MutableLiveData<Uri?> = MutableLiveData()
    val profileImgUri: MutableLiveData<Uri?> = _profileImgUri

    private val _profileImgStr: MutableLiveData<String?> = MutableLiveData()
    val profileImgStr: MutableLiveData<String?> = _profileImgStr

    private val _isSuccess: MutableLiveData<Boolean?> = MutableLiveData()
    val isSuccess: MutableLiveData<Boolean?> = _isSuccess

    var profileImgMultiPart: MultipartBody.Part? = null

    fun setProfileImg(uri: Uri, file: File) {
        _profileImgUri.value = uri
        val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        profileImgMultiPart =
            MultipartBody.Part.createFormData("profileImg", file.name, requestFile)
    }

    fun getNotes() = viewModelScope.launch {
        when (val value = getNotesUseCase()) {
            is Resource.Success<MyPageNote> -> {
                _etcNotesListData.value = value.data.myETCMessages
                _reviewNotesListData.value = value.data.myReviewMessages
            }
            is Resource.Error -> {
                Log.e("getNotes", "getNotes: ${value.errorMessage}")
            }
        }
    }

    fun getDetailData(messageId: String) = viewModelScope.launch {
        when (val value = getCampsiteMessageDetailInfoUseCase(messageId)) {
            is Resource.Success<CampsiteMessageDetailInfo> -> {
                _detailData.value = value.data
            }
            is Resource.Error -> {
                Log.e("getDetailData", "getDetailData: ${value.errorMessage}")
            }
        }
    }

    fun getInfo() = viewModelScope.launch {
        when (val value = getUserInfoUseCase()) {
            is Resource.Success<MyPageUser> -> {
                _nickname.value = value.data.name
                _profileImgStr.value = value.data.imagePath
            }
            is Resource.Error -> {
                Log.e("getUserInfo", "getUserInfo: ${value.errorMessage}")
            }
        }
    }

    // 프로필 이미지를 바꿔 등록
    fun updateProfile() {
        viewModelScope.launch {
            when (val value =
                editUserInfoUseCase(
                    requireNotNull(_nickname.value),
                    true,
                    profileImgMultiPart
                )) {
                is Resource.Success<User> -> {
                    ApplicationClass.preferences.nickname = _nickname.value
                    _isSuccess.value = true
                }
                is Resource.Error -> {
                    Log.e("updateprofile", "updateProfile: ${value.errorMessage}")
                }
            }
        }
    }

    // 프로필 이미지를 바꾸지 않고 등록
    fun updateProfileWithExistingImg() {
        viewModelScope.launch {
            when (val value =
                editUserInfoUseCase(
                    requireNotNull(_nickname.value),
                    false,
                    null
                )) {
                is Resource.Success<User> -> {
                    ApplicationClass.preferences.nickname = _nickname.value
                    _isSuccess.value = true
                }
                is Resource.Error -> {
                    Log.e("updateProfileWithExistingImg", "updateProfile: ${value.errorMessage}")
                }
            }
        }
    }

    // 프로필 이미지를 지우고 등록
    fun updateProfileWithoutImg() {
        viewModelScope.launch {
            when (val value =
                editUserInfoUseCase(
                    requireNotNull(_nickname.value),
                    true,
                    null
                )) {
                is Resource.Success<User> -> {
                    ApplicationClass.preferences.nickname = _nickname.value
                    _isSuccess.value = true
                }
                is Resource.Error -> {
                    Log.e("updateprofileWithoutImg", "updateProfile: ${value.errorMessage}")
                }
            }
        }
    }

    fun checkDuplication() {
        viewModelScope.launch {
            when (val value = checkDuplicationUseCase.checkDuplication(_nickname.value!!)) {
                is Resource.Success<Boolean> -> {
                    _isDuplicate.value = value.data
                }
                is Resource.Error -> {
                    Log.e("checkDuplication", "checkDuplication: ${value.errorMessage}")
                }
            }
        }
    }

    fun checkSame() {
        _isSame.value = _nickname.value == ApplicationClass.preferences.nickname
    }

    fun requestLogout() {
        viewModelScope.launch {
            when (val value = requestLogoutUseCase(
                LogoutRequest(
                    ApplicationClass.preferences.accessToken!!,
                    ApplicationClass.preferences.fcmToken!!,
                    ApplicationClass.preferences.refreshToken!!)
                )
            ) {
                is Resource.Success<Boolean> -> {
                    _isLoggedOut.value = value.data
                }
                is Resource.Error -> {
                    Log.e("requestLogout", "requestLogout: ${value.errorMessage}")
                }
            }
        }
    }
}