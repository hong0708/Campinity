package com.ssafy.campinity.presentation.community.campsite

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.community.CampsiteMessageDetailInfo
import com.ssafy.campinity.domain.entity.community.MarkerLocation
import com.ssafy.campinity.domain.usecase.community.CreateCampsiteMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CommunityCampsiteDialogViewModel @Inject constructor(
    private val createCampsiteMessageUseCase: CreateCampsiteMessageUseCase
) : ViewModel() {

    private val _markerLocation: MutableLiveData<MarkerLocation> =
        MutableLiveData(MarkerLocation(0.0, 0.0))
    val markerLocation: MutableLiveData<MarkerLocation> = _markerLocation

    private val _content: MutableLiveData<String> = MutableLiveData("")
    val content: MutableLiveData<String> = _content

    private val _file: MutableLiveData<Uri?> = MutableLiveData(null)
    val file: MutableLiveData<Uri?> = _file

    private var imgMultiPart: MultipartBody.Part? = null

    private val _isSucceed: MutableLiveData<Boolean> = MutableLiveData(false)
    val isSucceed: LiveData<Boolean> = _isSucceed

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