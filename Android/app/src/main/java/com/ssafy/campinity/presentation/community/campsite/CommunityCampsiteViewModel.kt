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
import com.ssafy.campinity.domain.entity.community.MarkerLocation
import com.ssafy.campinity.domain.usecase.community.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CommunityCampsiteViewModel @Inject constructor(
    private val getCampsiteBriefInfoByCampNameUseCase: GetCampsiteBriefInfoByCampNameUseCase,
    private val getCampsiteBriefInfoByUserLocationUseCase: GetCampsiteBriefInfoByUserLocationUseCase,
    private val getCampsiteMessageBriefInfoByScopeUseCase: GetCampsiteMessageBriefInfoByScopeUseCase,
    private val createCampsiteMessageUseCase: CreateCampsiteMessageUseCase,
    private val getCampsiteMessageDetailInfoUseCase: GetCampsiteMessageDetailInfoUseCase
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

    private val _markerLocation: MutableLiveData<MarkerLocation> =
        MutableLiveData(MarkerLocation(0.0, 0.0))
    val markerLocation: MutableLiveData<MarkerLocation> = _markerLocation

    private val _isSucceed: MutableLiveData<Boolean> = MutableLiveData(false)
    val isSucceed: LiveData<Boolean> = _isSucceed

    private val _content: MutableLiveData<String> = MutableLiveData("")
    val content: MutableLiveData<String> = _content

    private var imgMultiPart: MultipartBody.Part? = null

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

    fun createCommunityCampsiteMessage(
        messageCategory: String,
        campsiteId: String
    ) = viewModelScope.launch {
        when (
            val value = createCampsiteMessageUseCase(
                messageCategory,
                imgMultiPart,
                requireNotNull(markerLocation.value?.latitude),
                campsiteId,
                requireNotNull(content.value),
                requireNotNull(markerLocation.value?.longitude),

                )) {
            is Resource.Success<CampsiteMessageDetailInfo> -> {
                _isSucceed.value = true
            }
            is Resource.Error -> {
                Log.e(
                    "createCommunityCampsiteMessage",
                    "createCommunityCampsiteMessage: ${value.errorMessage}"
                )
                _isSucceed.value = false
            }
        }
    }

    fun setPicture(uri: Uri?, file: File?) {
        viewModelScope.launch {
            _file.value = uri
            val requestFile = file?.asRequestBody("image/jpeg".toMediaTypeOrNull())
            imgMultiPart =
                requestFile?.let { MultipartBody.Part.createFormData("file", file.name, it) }
        }
    }
}