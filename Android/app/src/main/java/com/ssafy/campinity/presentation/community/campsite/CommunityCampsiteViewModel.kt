package com.ssafy.campinity.presentation.community.campsite

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.community.CampsiteBriefInfo
import com.ssafy.campinity.domain.entity.community.CampsiteMessageBriefInfo
import com.ssafy.campinity.domain.entity.community.CampsiteMessageDetailInfo
import com.ssafy.campinity.domain.entity.fcm.FCMToken
import com.ssafy.campinity.domain.entity.user.UserProfile
import com.ssafy.campinity.domain.usecase.community.*
import com.ssafy.campinity.domain.usecase.fcm.RequestSubscribeCampSiteUseCase
import com.ssafy.campinity.domain.usecase.user.GetUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityCampsiteViewModel @Inject constructor(
    private val getCampsiteBriefInfoByCampNameUseCase: GetCampsiteBriefInfoByCampNameUseCase,
    private val getCampsiteBriefInfoByUserLocationUseCase: GetCampsiteBriefInfoByUserLocationUseCase,
    private val getCampsiteMessageBriefInfoByScopeUseCase: GetCampsiteMessageBriefInfoByScopeUseCase,
    private val getCampsiteMessageDetailInfoUseCase: GetCampsiteMessageDetailInfoUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val requestSubscribeCampSiteUseCase: RequestSubscribeCampSiteUseCase
) : ViewModel() {

    private val _campsiteBriefInfo = MutableLiveData<List<CampsiteBriefInfo>>()
    val campsiteBriefInfo: LiveData<List<CampsiteBriefInfo>> = _campsiteBriefInfo

    private val _campsiteMessageBriefInfo = MutableLiveData<List<CampsiteMessageBriefInfo>>()
    val campsiteMessageBriefInfo: LiveData<List<CampsiteMessageBriefInfo>> =
        _campsiteMessageBriefInfo

    private val _campsiteMessageDetailInfo = MutableLiveData<CampsiteMessageDetailInfo>()
    val campsiteMessageDetailInfo: LiveData<CampsiteMessageDetailInfo> =
        _campsiteMessageDetailInfo

    private val _file: MutableLiveData<Uri?> = MutableLiveData(null)
    val file: MutableLiveData<Uri?> = _file

    private val _content: MutableLiveData<String> = MutableLiveData("")
    val content: MutableLiveData<String> = _content

    private val _profileImgStr: MutableLiveData<String?> = MutableLiveData()
    val profileImgStr: MutableLiveData<String?> = _profileImgStr

    private val _isUserIn: MutableLiveData<Boolean> = MutableLiveData(false)
    val isUserIn: MutableLiveData<Boolean> = _isUserIn

    fun checkIsUserIn(check: Boolean?) {
        if (check !=null){
            _isUserIn.value = check!!
        }
    }

    fun getUserProfile() {
        viewModelScope.launch {
            when (val value = getUserProfileUseCase()) {
                is Resource.Success<UserProfile> -> {
                    _profileImgStr.value = value.data.profileImg
                }
                is Resource.Error -> {
                    Log.d("getUserProfile", "getUserProfile: ${value.errorMessage}")
                }
            }
        }
    }

    fun getCampsiteBriefInfoByCampName() = viewModelScope.launch {
        when (val value = getCampsiteBriefInfoByCampNameUseCase(requireNotNull(content.value))) {
            is Resource.Success<List<CampsiteBriefInfo>> -> {
                val campsiteBriefInfoList = value.data
                _campsiteBriefInfo.value = campsiteBriefInfoList
            }
            is Resource.Error -> {
                Log.d("getCampsiteBriefInfoByCampName", "NoteQuestions: ${value.errorMessage}")
                _campsiteBriefInfo.value = listOf()
            }
        }
    }

    fun getCampsiteBriefInfoByUserLocation(
        bottomRightLat: Double,
        bottomRightLng: Double,
        topLeftLat: Double,
        topLeftLng: Double
    ) = viewModelScope.launch {
        when (val value = getCampsiteBriefInfoByUserLocationUseCase(
            bottomRightLat,
            bottomRightLng,
            topLeftLat,
            topLeftLng
        )) {
            is Resource.Success<List<CampsiteBriefInfo>> -> {
                val campsiteBriefInfoList = value.data
                _campsiteBriefInfo.value = campsiteBriefInfoList
            }
            is Resource.Error -> {
                Log.d(
                    "getCampsiteBriefInfoByUserLocation",
                    "getCampsiteBriefInfoByUserLocation: ${value.errorMessage}"
                )
                _campsiteBriefInfo.value = listOf()
            }
        }
    }

    fun getCampsiteMessageBriefInfoByScope(
        bottomRightLat: Double,
        bottomRightLng: Double,
        campsiteId: String,
        topLeftLat: Double,
        topLeftLng: Double
    ) = viewModelScope.launch {
        when (val value = getCampsiteMessageBriefInfoByScopeUseCase(
            bottomRightLat,
            bottomRightLng,
            campsiteId,
            topLeftLat,
            topLeftLng
        )) {
            is Resource.Success<List<CampsiteMessageBriefInfo>> -> {
                val campsiteMessageBriefInfoList = value.data
                _campsiteMessageBriefInfo.value = campsiteMessageBriefInfoList
            }
            is Resource.Error -> {
                Log.d(
                    "getCampsiteMessageBriefInfoByScope",
                    "getCampsiteMessageBriefInfoByScope: ${value.errorMessage}"
                )
                _campsiteMessageBriefInfo.value = listOf()
            }
        }
    }

    fun getCampsiteMessageDetailInfo(messageId: String) = viewModelScope.launch {
        when (val value = getCampsiteMessageDetailInfoUseCase(messageId)) {
            is Resource.Success<CampsiteMessageDetailInfo> -> {
                val campsiteMessageDetailInfo = value.data
                _campsiteMessageDetailInfo.value = campsiteMessageDetailInfo
            }
            is Resource.Error -> {
                Log.d(
                    "getCampsiteMessageDetailInfo",
                    "getCampsiteMessageDetailInfo: ${value.errorMessage}"
                )
            }
        }
    }

    fun subscribeCampSite(campsiteId: String, fcmToken: String) = viewModelScope.launch {
        when (val value = requestSubscribeCampSiteUseCase(campsiteId, fcmToken)) {
            is Resource.Success<FCMToken> -> {}
            is Resource.Error -> {
                Log.e("getCampsiteMessageDetailInfo", "${value.errorMessage}")
            }
        }
    }
}