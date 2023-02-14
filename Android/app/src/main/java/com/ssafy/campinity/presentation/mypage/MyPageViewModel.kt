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
import com.ssafy.campinity.domain.entity.curation.CurationItem
import com.ssafy.campinity.domain.entity.mypage.MyPageNote
import com.ssafy.campinity.domain.entity.mypage.MyPageUser
import com.ssafy.campinity.domain.entity.mypage.ScrapCampsite
import com.ssafy.campinity.domain.entity.user.User
import com.ssafy.campinity.domain.usecase.community.GetCampsiteMessageDetailInfoUseCase
import com.ssafy.campinity.domain.usecase.curation.GetScrapCurationsUseCase
import com.ssafy.campinity.domain.usecase.mypage.*
import com.ssafy.campinity.domain.usecase.user.CheckDuplicationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
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
    private val editUserInfoUseCase: EditUserInfoUseCase,
    private val getScrapCurationsUseCase: GetScrapCurationsUseCase,
    private val getScrapCampsitesUseCase: GetScrapCampsitesUseCase
) : ViewModel() {

    private val _etcNotesListData: MutableLiveData<List<CampsiteMessageDetailInfo>> =
        MutableLiveData()
    val etcNotesListData: LiveData<List<CampsiteMessageDetailInfo>?> = _etcNotesListData

    private val _reviewNotesListData: MutableLiveData<List<CampsiteMessageDetailInfo>> =
        MutableLiveData()
    val reviewNotesListData: LiveData<List<CampsiteMessageDetailInfo>?> = _reviewNotesListData

    private val _curationListData: MutableLiveData<List<CurationItem>?> = MutableLiveData()
    val curationListData: LiveData<List<CurationItem>?> = _curationListData

    private val _campsiteListData: MutableLiveData<List<ScrapCampsite>?> = MutableLiveData()
    val campsiteListData: LiveData<List<ScrapCampsite>?> = _campsiteListData

    private val _detailData: MutableLiveData<CampsiteMessageDetailInfo?> = MutableLiveData()
    val detailData: LiveData<CampsiteMessageDetailInfo?> = _detailData

    private val _isDuplicate: MutableLiveData<Boolean?> = MutableLiveData(true)
    val isDuplicate: MutableLiveData<Boolean?> = _isDuplicate

    private val _isLoggedOut: MutableLiveData<Boolean?> = MutableLiveData()
    val isLoggedOut: LiveData<Boolean?> = _isLoggedOut

    private val _nickname: MutableLiveData<String> = MutableLiveData()
    val nickname: MutableLiveData<String> = _nickname

    private val _profileImgUri: MutableLiveData<Uri?> = MutableLiveData()
    val profileImgUri: MutableLiveData<Uri?> = _profileImgUri

    private val _profileImgStr: MutableLiveData<String?> = MutableLiveData()
    val profileImgStr: MutableLiveData<String?> = _profileImgStr

    private val _isSuccess: MutableLiveData<Boolean?> = MutableLiveData()
    val isSuccess: MutableLiveData<Boolean?> = _isSuccess

    private val _nicknameCheck: MutableLiveData<String> = MutableLiveData()
    val nicknameCheck: MutableLiveData<String> = _nicknameCheck

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

    fun getScrapCurations() = viewModelScope.launch {
        when (val value = getScrapCurationsUseCase()) {
            is Resource.Success<List<CurationItem>> -> {
                _curationListData.value = value.data
            }
            is Resource.Error -> {
                Log.e("getScrapCurations", "getScrapCurations: ${value.errorMessage}")
            }
        }
    }

    fun getScrapCampsites() = viewModelScope.launch {
        when (val value = getScrapCampsitesUseCase()) {
            is Resource.Success<List<ScrapCampsite>> -> {
                _campsiteListData.value = value.data
            }
            is Resource.Error -> {
                Log.e("getScrapCampsites", "getScrapCampsites: ${value.errorMessage}")
            }
        }
    }

    fun getInfo() = viewModelScope.launch {
        when (val value = getUserInfoUseCase()) {
            is Resource.Success<MyPageUser> -> {
                _nickname.value = value.data.name
                _profileImgStr.value = value.data.imagePath
                ApplicationClass.preferences.nickname = value.data.name
            }
            is Resource.Error -> {
                Log.e("getUserInfo", "getUserInfo: ${value.errorMessage}")
            }
        }
    }

    // 프로필 이미지를 바꿔 등록
    fun updateProfile(nickname: String) {
        viewModelScope.launch {
            when (val value = editUserInfoUseCase(nickname, true, profileImgMultiPart)) {
                is Resource.Success<User> -> {
                    ApplicationClass.preferences.nickname = nickname
                    _nickname.value = nickname
                    _isSuccess.value = true
                }
                is Resource.Error -> {
                    Log.e("updateprofile", "updateProfile: ${value.errorMessage}")
                }
            }
        }
    }

    // 프로필 이미지를 바꾸지 않고 등록
    fun updateProfileWithExistingImg(nickname: String) {
        viewModelScope.launch {
            when (val value = editUserInfoUseCase(nickname, false, null)) {
                is Resource.Success<User> -> {
                    ApplicationClass.preferences.nickname = nickname
                    _nickname.value = nickname
                    _isSuccess.value = true
                }
                is Resource.Error -> {
                    Log.e("updateProfileWithExistingImg", "updateProfile: ${value.errorMessage}")
                }
            }
        }
    }

    // 프로필 이미지를 지우고 등록
    fun updateProfileWithoutImg(nickname: String) {
        viewModelScope.launch {
            when (val value = editUserInfoUseCase(nickname, true, null)) {
                is Resource.Success<User> -> {
                    ApplicationClass.preferences.nickname = nickname
                    _nickname.value = nickname
                    _isSuccess.value = true
                }
                is Resource.Error -> {
                    Log.e("updateprofileWithoutImg", "updateProfile: ${value.errorMessage}")
                }
            }
        }
    }

    fun requestLogout() {
        viewModelScope.launch {
            when (val value = requestLogoutUseCase(
                LogoutRequest(
                    ApplicationClass.preferences.accessToken!!,
                    ApplicationClass.preferences.fcmToken!!,
                    ApplicationClass.preferences.refreshToken!!
                )
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

    suspend fun checkDuplication(nickname: String) = viewModelScope.async {
        when (val value = checkDuplicationUseCase.checkDuplication(nickname)) {
            is Resource.Success<Boolean> -> {
                _isDuplicate.value = value.data
                return@async 1
            }
            is Resource.Error -> {
                Log.e("checkDuplication", "checkDuplication: ${value.errorMessage}")
                return@async 0
            }
        }
    }.await()

    fun clearData() {
        _detailData.value = null
    }
}