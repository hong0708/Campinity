package com.ssafy.campinity.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.campinity.ApplicationClass
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.service.FirebaseService
import com.ssafy.campinity.domain.entity.collection.CollectionItem
import com.ssafy.campinity.domain.entity.home.HomeBanner
import com.ssafy.campinity.domain.entity.home.RankingCampsiteItem
import com.ssafy.campinity.domain.usecase.home.GetHomeBannersUseCase
import com.ssafy.campinity.domain.usecase.home.GetHomeCollectionsUseCase
import com.ssafy.campinity.domain.usecase.home.GetRankingCampsiteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeBannersUseCase: GetHomeBannersUseCase,
    private val getHomeCollectionsUseCase: GetHomeCollectionsUseCase,
    private val getRankingCampsiteUseCase: GetRankingCampsiteUseCase
) : ViewModel() {

    private val _homeBanners: MutableLiveData<List<HomeBanner>?> = MutableLiveData()
    val homeBanners: LiveData<List<HomeBanner>?> = _homeBanners

    private val _homeCollections: MutableLiveData<List<CollectionItem>?> = MutableLiveData()
    val homeCollections: LiveData<List<CollectionItem>?> = _homeCollections

    private val _highestCampsites: MutableLiveData<List<RankingCampsiteItem>?> = MutableLiveData()
    val highestCampsites: LiveData<List<RankingCampsiteItem>?> = _highestCampsites

    private val _hottestCampsites: MutableLiveData<List<RankingCampsiteItem>?> = MutableLiveData()
    val hottestCampsites: LiveData<List<RankingCampsiteItem>?> = _hottestCampsites

    fun getHomeBanners() = viewModelScope.launch {
        when (val value = getHomeBannersUseCase()) {
            is Resource.Success<List<HomeBanner>> -> {
                _homeBanners.value = value.data
            }
            is Resource.Error -> {
                Log.e("getHomeBanners", "getHomeBanners: ${value.errorMessage}")
            }
        }
    }

    fun getHomeCollections() = viewModelScope.launch {
        when (val value = getHomeCollectionsUseCase()) {
            is Resource.Success<List<CollectionItem>> -> {
                _homeCollections.value = value.data
            }
            is Resource.Error -> {
                Log.e("getHomeCollections", "getHomeCollections: ${value.errorMessage}")
            }
        }
    }

    fun getHighestCampsites() = viewModelScope.launch {
        when (val value = getRankingCampsiteUseCase.getHighestCampsites()) {
            is Resource.Success<List<RankingCampsiteItem>> -> {
                _highestCampsites.value = value.data
            }
            is Resource.Error -> {
                Log.e("getHighestCampsites", "getHighestCampsites: ${value.errorMessage}")
            }
        }
    }

    fun getHottestCampsites() = viewModelScope.launch {
        when (val value = getRankingCampsiteUseCase.getHottestCampsites()) {
            is Resource.Success<List<RankingCampsiteItem>> -> {
                _hottestCampsites.value = value.data
            }
            is Resource.Error -> {
                Log.e("getHottestCampsites", "getHottestCampsites: ${value.errorMessage}")
            }
        }
    }

    fun requestCurrentToken() = viewModelScope.launch {
        val result = FirebaseService().getCurrentToken()
        ApplicationClass.preferences.fcmToken = result
    }
}