package com.ssafy.campinity.presentation.curation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.curation.CurationDetailItem
import com.ssafy.campinity.domain.entity.curation.CurationItem
import com.ssafy.campinity.domain.usecase.curation.GetCurationDetailUseCase
import com.ssafy.campinity.domain.usecase.curation.GetCurationsUseCase
import com.ssafy.campinity.domain.usecase.curation.ScrapCurationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurationViewModel @Inject constructor(
    private val getCurationsUseCase: GetCurationsUseCase,
    private val getCurationDetailUseCase: GetCurationDetailUseCase,
    private val scrapCurationUseCase: ScrapCurationUseCase
) : ViewModel() {

    val categoryState: MutableLiveData<String?> = MutableLiveData()

    private val _curationData: MutableLiveData<CurationDetailItem?> = MutableLiveData()
    val curationData: LiveData<CurationDetailItem?> = _curationData

    private val _curationListData: MutableLiveData<List<CurationItem>?> = MutableLiveData()
    val curationListData: LiveData<List<CurationItem>?> = _curationListData

    fun getCurations(curationCategory: String) = viewModelScope.launch {
        when (val value = getCurationsUseCase(curationCategory)) {
            is Resource.Success<List<CurationItem>> -> {
                _curationListData.value = value.data
            }
            is Resource.Error -> {
                Log.e("getCurations", "getCurations: ${value.errorMessage}")
            }
        }
    }

    fun getCuration(curationId: String) = viewModelScope.launch {
        when (val value = getCurationDetailUseCase(curationId)) {
            is Resource.Success<CurationDetailItem> -> {
                _curationData.value = value.data
            }
            is Resource.Error -> {
                Log.e("getCuration", "getCuration: ${value.errorMessage}")
            }
        }
    }

    suspend fun scrapCuration(curationId: String) = viewModelScope.async {
        when (val value = scrapCurationUseCase(curationId)) {
            is Resource.Success<Boolean> -> return@async value.data.toString()
            is Resource.Error -> {
                Log.e("scrapCuration", "scrapCuration: ${value.errorMessage}")
                return@async "error"
            }
        }
    }.await()
}