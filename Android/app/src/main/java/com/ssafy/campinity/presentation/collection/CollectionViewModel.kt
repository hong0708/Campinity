package com.ssafy.campinity.presentation.collection

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.collection.CollectionItem
import com.ssafy.campinity.domain.usecase.collection.GetCollectionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val getCollectionsUseCase: GetCollectionsUseCase
) : ViewModel() {

    private val _collectionListData: MutableLiveData<List<CollectionItem>?> = MutableLiveData()
    val collectionListData: LiveData<List<CollectionItem>?> = _collectionListData

    fun getCollections() = viewModelScope.launch {
        when (val value = getCollectionsUseCase()) {
            is Resource.Success<List<CollectionItem>> -> {
                _collectionListData.value = value.data
            }
            is Resource.Error -> {
                Log.e("getCollections", "getCollections: ${value.errorMessage}", )
            }
        }
    }
}