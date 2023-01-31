package com.ssafy.campinity.presentation.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.search.CampsiteBriefInfo
import com.ssafy.campinity.domain.usecase.search.GetCampsitesByAreaUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val getCampsitesByAreaUseCase: GetCampsitesByAreaUseCase
) : ViewModel() {

    private val _campsiteListData: MutableLiveData<List<CampsiteBriefInfo>?> = MutableLiveData()
    val campsiteListData: LiveData<List<CampsiteBriefInfo>?> = _campsiteListData

    fun getCampsitesByArea() = viewModelScope.launch {
        when (val value = getCampsitesByAreaUseCase()) {
            is Resource.Success<List<CampsiteBriefInfo>> -> {
                _campsiteListData.value = value.data
            }
            is Resource.Error -> {
                Log.e("getCampsitesByArea", "getCampsitesByArea: ${value.errorMessage}")
            }
        }
    }
}