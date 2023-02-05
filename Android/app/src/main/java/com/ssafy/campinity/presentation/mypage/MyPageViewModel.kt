package com.ssafy.campinity.presentation.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.community.CampsiteMessageDetailInfo
import com.ssafy.campinity.domain.entity.mypage.MyPageNote
import com.ssafy.campinity.domain.usecase.community.GetCampsiteMessageDetailInfoUseCase
import com.ssafy.campinity.domain.usecase.mypage.GetNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject  constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val getCampsiteMessageDetailInfoUseCase: GetCampsiteMessageDetailInfoUseCase
) : ViewModel() {

    private val _etcNotesListData: MutableLiveData<List<CampsiteMessageDetailInfo>> = MutableLiveData()
    val etcNotesListdata: LiveData<List<CampsiteMessageDetailInfo>?> = _etcNotesListData

    private val _reviewNotesListData: MutableLiveData<List<CampsiteMessageDetailInfo>> = MutableLiveData()
    val reviewNotesListData: LiveData<List<CampsiteMessageDetailInfo>?> = _reviewNotesListData

    private val _detailData: MutableLiveData<CampsiteMessageDetailInfo?> = MutableLiveData()
    val detailData: LiveData<CampsiteMessageDetailInfo?> = _detailData

    fun getNotes() = viewModelScope.launch {
        when (val value = getNotesUseCase()) {
            is Resource.Success<MyPageNote> -> {
                _etcNotesListData.value = value.data.myETCMessages
                _reviewNotesListData.value = value.data.myReviewMessages
            }
            is Resource.Error -> {
                Log.e("getNotes", "getNotes: ${value.errorMessage}")
            }
        }
    }

    fun getDetailData(messageId: String) = viewModelScope.launch {
        when (val value = getCampsiteMessageDetailInfoUseCase(messageId)) {
            is Resource.Success<CampsiteMessageDetailInfo> -> {
                _detailData.value = value.data
            }
            is Resource.Error -> {
                Log.e("getDetailData", "getDetailData: ${value.errorMessage}")
            }
        }
    }
}