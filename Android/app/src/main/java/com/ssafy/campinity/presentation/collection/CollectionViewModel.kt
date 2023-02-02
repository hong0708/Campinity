package com.ssafy.campinity.presentation.collection

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.collection.CollectionItem
import com.ssafy.campinity.domain.usecase.collection.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val getCollectionsUseCase: GetCollectionsUseCase,
    private val createCollectionUseCase: CreateCollectionUseCase,
    private val getCollectionDetailUseCase: GetCollectionDetailUseCase,
    private val deleteCollectionUseCase: DeleteCollectionUseCase,
    private val updateCollectionUseCase: UpdateCollectionUseCase
) : ViewModel() {

    private val _collectionData: MutableLiveData<CollectionItem?> = MutableLiveData()
    val collectionData: LiveData<CollectionItem?> = _collectionData

    private val _collectionListData: MutableLiveData<List<CollectionItem>?> = MutableLiveData()
    val collectionListData: LiveData<List<CollectionItem>?> = _collectionListData

    private val _campsiteName: MutableLiveData<String> = MutableLiveData("")
    val campsiteName: MutableLiveData<String> = _campsiteName

    private val _content: MutableLiveData<String> = MutableLiveData("")
    val content: MutableLiveData<String> = _content

    private val _date: MutableLiveData<String> = MutableLiveData("")
    val date: MutableLiveData<String> = _date

    private val _file: MutableLiveData<Uri?> = MutableLiveData(null)
    val file: MutableLiveData<Uri?> = _file

    private val _isSucceed: MutableLiveData<Boolean> = MutableLiveData(false)
    val isSucceed: LiveData<Boolean> = _isSucceed

    private val _isDeleted: MutableLiveData<Boolean> = MutableLiveData(false)
    val isDeleted: LiveData<Boolean> = _isDeleted

    private val _isUpdated: MutableLiveData<Boolean> = MutableLiveData(false)
    val isUpdated: LiveData<Boolean> = _isUpdated

    private val _isValid: MutableLiveData<Boolean> = MutableLiveData(false)
    val isValid: LiveData<Boolean> = _isValid

    private var imgMultiPart: MultipartBody.Part? = null

    fun getCollection(collectionId: String) = viewModelScope.launch {
        when (val value = getCollectionDetailUseCase(collectionId)) {
            is Resource.Success<CollectionItem> -> {
                _collectionData.value = value.data
            }
            is Resource.Error -> {
                Log.e("getDetail", "getDetail: ${value.errorMessage}")
            }
        }
    }

    fun getCollections() = viewModelScope.launch {
        when (val value = getCollectionsUseCase()) {
            is Resource.Success<List<CollectionItem>> -> {
                _collectionListData.value = value.data
            }
            is Resource.Error -> {
                Log.e("getCollections", "getCollections: ${value.errorMessage}")
            }
        }
    }

    fun deleteCollection(collectionId: String) = viewModelScope.launch {
        when (val value = deleteCollectionUseCase(collectionId)) {
            is Resource.Success<String> -> {
                _isDeleted.value = true
            }
            is Resource.Error -> {
                Log.e("deleteCollection", "deleteCollection: ${value.errorMessage}")
            }
        }
    }

    fun updateCollection(collectionId: String) = viewModelScope.launch {
        when (val value = updateCollectionUseCase(collectionId)) {
            is Resource.Success<CollectionItem> -> {
                _isUpdated.value = true
            }
            is Resource.Error -> {
                Log.e("updateCollection", "updateCollection: ${value.errorMessage}")
            }
        }
    }

    fun createCollection() = viewModelScope.launch {
        when (
            val value = createCollectionUseCase(
                campsiteName.value ?: "",
                content.value ?: "",
                date.value ?: "",
                imgMultiPart
            )) {
            is Resource.Success<CollectionItem> -> {
                _isSucceed.value = true
            }
            is Resource.Error -> {
                Log.e("createCollection", "createCollection: ${value.errorMessage}")
                _isSucceed.value = false
            }
        }
    }

    fun setPicture(uri: Uri, file: File) {
        viewModelScope.launch {
            _file.value = uri
            val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            imgMultiPart = MultipartBody.Part.createFormData("file", file.name, requestFile)
        }
    }
}