package com.ssafy.campinity.presentation.community.campsite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.community.CampsiteBriefInfo
import com.ssafy.campinity.domain.entity.community.MarkerLocation
import com.ssafy.campinity.domain.usecase.community.GetCampsiteBriefInfoByCampNameUseCase
import com.ssafy.campinity.domain.usecase.community.GetCampsiteBriefInfoByUserLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityCampsiteViewModel @Inject constructor(
    private val getCampsiteBriefInfoByCampNameUseCase: GetCampsiteBriefInfoByCampNameUseCase,
    private val getCampsiteBriefInfoByUserLocationUseCase: GetCampsiteBriefInfoByUserLocationUseCase
) : ViewModel() {

    private val _campsiteBriefInfo = MutableLiveData<List<CampsiteBriefInfo>>()
    val campsiteBriefInfo: LiveData<List<CampsiteBriefInfo>> = _campsiteBriefInfo

    private val _userLocation = MutableLiveData<MarkerLocation>()
    val userLocation: LiveData<MarkerLocation> = _userLocation

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
}