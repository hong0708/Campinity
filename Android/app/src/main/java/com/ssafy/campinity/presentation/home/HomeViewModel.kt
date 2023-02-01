package com.ssafy.campinity.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.home.HomeBanner
import com.ssafy.campinity.domain.usecase.home.GetHomeBannersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeBannersUseCase: GetHomeBannersUseCase
) : ViewModel() {

    private val _homeBanners: MutableLiveData<List<HomeBanner>?> = MutableLiveData()
    val homeBanners: LiveData<List<HomeBanner>?> = _homeBanners

    fun getHomeBanners() = viewModelScope.launch {
        when (val value = getHomeBannersUseCase()) {
            is Resource.Success<List<HomeBanner>> -> {
                _homeBanners.value = value.data
            }
            is Resource.Error -> {
                Log.e("getCollections", "getCollections: ${value.errorMessage}")
            }
        }
    }
}