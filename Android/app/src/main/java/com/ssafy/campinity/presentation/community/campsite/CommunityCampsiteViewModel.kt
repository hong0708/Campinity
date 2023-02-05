package com.ssafy.campinity.presentation.community.campsite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.community.CampsiteBriefInfo
import com.ssafy.campinity.domain.entity.community.CampsiteMessageBriefInfo
import com.ssafy.campinity.domain.usecase.community.GetCampsiteBriefInfoByCampNameUseCase
import com.ssafy.campinity.domain.usecase.community.GetCampsiteBriefInfoByUserLocationUseCase
import com.ssafy.campinity.domain.usecase.community.GetCampsiteMessageBriefInfoByScopeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityCampsiteViewModel @Inject constructor(
    private val getCampsiteBriefInfoByCampNameUseCase: GetCampsiteBriefInfoByCampNameUseCase,
    private val getCampsiteBriefInfoByUserLocationUseCase: GetCampsiteBriefInfoByUserLocationUseCase,
    private val getCampsiteMessageBriefInfoByScopeUseCase: GetCampsiteMessageBriefInfoByScopeUseCase
) : ViewModel() {

    private val _campsiteBriefInfo = MutableLiveData<List<CampsiteBriefInfo>>()
    val campsiteBriefInfo: LiveData<List<CampsiteBriefInfo>> = _campsiteBriefInfo

    private val _campsiteMessageBriefInfo = MutableLiveData<List<CampsiteMessageBriefInfo>>()
    val campsiteMessageBriefInfo: LiveData<List<CampsiteMessageBriefInfo>> = _campsiteMessageBriefInfo

    fun getCampsiteBriefInfoByCampName(campsiteName: String) = viewModelScope.launch {
        when (val value = getCampsiteBriefInfoByCampNameUseCase(campsiteName)) {
            is Resource.Success<List<CampsiteBriefInfo>> -> {
                val campsiteBriefInfoList = value.data
                _campsiteBriefInfo.value = campsiteBriefInfoList
            }
            is Resource.Error -> {
                Log.d("requestNoteQuestions", "NoteQuestions: ${value.errorMessage}")
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
                Log.d(
                    "getCampsiteBriefInfoByUserLocation",
                    "getCampsiteBriefInfoByUserLocation: ${value.data}"
                )
            }
            is Resource.Error -> {
                Log.d("getCampsiteBriefInfoByUserLocation", "NoteQuestions: ${value.errorMessage}")
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
                Log.d(
                    "getCampsiteMessageBriefInfoByScope",
                    "getCampsiteMessageBriefInfoByScope: ${value.data}"
                )
            }
            is Resource.Error -> {
                Log.d("getCampsiteMessageBriefInfoByScope", "getCampsiteMessageBriefInfoByScope: ${value.errorMessage}")
                _campsiteMessageBriefInfo.value = listOf()
            }
        }
    }
}