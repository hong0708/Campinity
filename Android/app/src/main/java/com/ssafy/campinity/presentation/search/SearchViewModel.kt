package com.ssafy.campinity.presentation.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.search.*
import com.ssafy.campinity.domain.usecase.search.GetCampsitesByAreaUseCase
import com.ssafy.campinity.domain.usecase.search.GetGugunUseCase
import com.ssafy.campinity.domain.usecase.search.GetSidoAllUseCase
import com.ssafy.campinity.domain.usecase.search.InsertAreaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getCampsitesByAreaUseCase: GetCampsitesByAreaUseCase,
    private val insertAreaUseCase: InsertAreaUseCase,
    private val getSidoAllUseCase: GetSidoAllUseCase,
    private val getGugunUseCase: GetGugunUseCase
) : ViewModel() {

    private val _campsiteListData: MutableLiveData<List<CampsiteBriefInfo>?> = MutableLiveData()
    val campsiteListData: LiveData<List<CampsiteBriefInfo>?> = _campsiteListData

    private val _sido: MutableLiveData<String> = MutableLiveData()
    val sido: LiveData<String> = _sido

    private val _sidoList: MutableLiveData<List<SidoItem>?> = MutableLiveData()
    val sidoList: LiveData<List<SidoItem>?> = _sidoList

    private val _gugunList: MutableLiveData<List<GugunItem>?> = MutableLiveData()
    val gugunList: LiveData<List<GugunItem>?> = _gugunList

    private val _areaList: MutableLiveData<ArrayList<AreaListItem>?> = MutableLiveData()
    val areaList: LiveData<ArrayList<AreaListItem>?> = _areaList

    private val _sidoNameList: MutableLiveData<ArrayList<String>> = MutableLiveData()
    val sidoNameList: LiveData<ArrayList<String>> = _sidoNameList

    fun setSido(sido: String) {
        viewModelScope.launch {
            _sido.value = sido
        }
    }

    fun mapGugun(gugun: List<String>): String {
        val result = StringBuilder()
        gugun.forEachIndexed { index, item ->
            if (index == 0) result.append(item)
            else result.append(" $item")
        }
        return result.toString()
    }

    fun makeAreaList(areaListItem: AreaListItem) {
        _areaList.value?.add(areaListItem)
        Log.d("makeAreaList", "makeAreaList: ${areaList.value}")
    }

    fun extractSidoName() {
        with(areaList.value) {
//            if (this != null) {
//                this.forEach { areaListItem ->
//                    _sidoNameList.value?.add(areaListItem.sidoName)
//                }
//            } else {
//                _sidoNameList.value = arrayListOf()
//            }

            this?.forEach { areaListItem ->
                _sidoNameList.value?.add(areaListItem.sidoName)
            }
        }
    }

    suspend fun insertAreaDataBase(stringArray: Array<String>) {
        val sidoName = stringArray[0]
        for (i in 1 until stringArray.size) {
            val gugunName = stringArray[i].split("(")[0]
            val campsiteCount = stringArray[i].split("(")[1].split(")")[0].toInt()

            insertAreaUseCase(AreaEntity(sidoName, gugunName, campsiteCount))
        }
    }

    fun getSidoAll() = viewModelScope.launch {
        when (val value = getSidoAllUseCase()) {
            is Resource.Success<List<SidoItem>> -> {
                _sidoList.value = value.data
                Log.d("getSidoAll", "getSidoAll1: ${value.data}")
                Log.d("getSidoAll", "getSidoAll2: ${sidoList.value}")
            }
            is Resource.Error -> {
                Log.e("getSidoAll", "getSidoAll: ${value.errorMessage}")
            }
        }
    }

    fun getGugun(sidoName: String) = viewModelScope.launch {
        Log.e("getGugun", "getGugun is called")
        when (val value = getGugunUseCase(sidoName)) {
            is Resource.Success<List<GugunItem>> -> {
                _gugunList.value = value.data
                Log.e("getGugun", "getGugun: ${value.data}")
                Log.e("getGugun", "getGugun: ${gugunList.value}")
            }
            is Resource.Error -> {
                Log.e("getGugun", "getGugun: ${value.errorMessage}")
            }
        }
    }

    fun getCampsitesByArea(sido: String, gugun: String) = viewModelScope.launch {
        when (val value = getCampsitesByAreaUseCase(sido, gugun)) {
            is Resource.Success<List<CampsiteBriefInfo>> -> {
                _campsiteListData.value = value.data
            }
            is Resource.Error -> {
                Log.e("getCampsitesByArea", "getCampsitesByArea: ${value.errorMessage}")
            }
        }
    }
}