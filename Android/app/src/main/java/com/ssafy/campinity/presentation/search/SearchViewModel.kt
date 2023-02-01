package com.ssafy.campinity.presentation.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.search.CampsiteBriefInfo
import com.ssafy.campinity.domain.usecase.search.GetCampsitesByAreaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getCampsitesByAreaUseCase: GetCampsitesByAreaUseCase
) : ViewModel() {

    private val _campsiteListData: MutableLiveData<List<CampsiteBriefInfo>?> = MutableLiveData()
    val campsiteListData: LiveData<List<CampsiteBriefInfo>?> = _campsiteListData

    fun setGugun(gugun: List<String>): String {
        val result = StringBuilder()
        gugun.forEachIndexed { index, item ->
            if (index == 0) result.append(item)
            else result.append(" $item")
        }
        return result.toString()
    }

    fun getCampsitesByArea(sido: String, gugun: String) = viewModelScope.launch {
        when (val value = getCampsitesByAreaUseCase(sido, gugun)) {
            is Resource.Success<List<CampsiteBriefInfo>> -> {
                _campsiteListData.value = value.data
                Log.d("getCampsitesByArea", "getCampsitesByArea: ${value.data}")
            }
            is Resource.Error -> {
                Log.e("getCampsitesByArea", "getCampsitesByArea: ${value.errorMessage}")
            }
        }
    }
}