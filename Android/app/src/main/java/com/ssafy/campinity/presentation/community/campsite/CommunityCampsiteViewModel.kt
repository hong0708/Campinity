package com.ssafy.campinity.presentation.community.campsite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.campinity.domain.entity.community.CampsiteTitle
import com.ssafy.campinity.domain.entity.community.MarkerLocation
import kotlinx.coroutines.launch

class CommunityCampsiteViewModel(

) : ViewModel() {

    private val _campsiteTitle = MutableLiveData<List<CampsiteTitle>>()
    val campsiteTitle: LiveData<List<CampsiteTitle>> = _campsiteTitle

    private val _userLocation = MutableLiveData<MarkerLocation>()
    val userLocation: LiveData<MarkerLocation> = _userLocation

    fun getCampsiteTitle(campsiteId: String) = viewModelScope.launch {

    }

    fun getUserLocation() = viewModelScope.launch {

    }
}