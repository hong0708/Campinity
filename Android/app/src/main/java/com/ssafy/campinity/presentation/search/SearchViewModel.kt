package com.ssafy.campinity.presentation.search

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.campinity.R
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.search.SearchFilterRequest
import com.ssafy.campinity.data.remote.datasource.search.SearchReviewRequest
import com.ssafy.campinity.domain.entity.community.CampsiteMessageDetailInfo
import com.ssafy.campinity.domain.entity.search.*
import com.ssafy.campinity.domain.usecase.community.GetCampsiteMessageDetailInfoUseCase
import com.ssafy.campinity.domain.usecase.search.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getCampsitesByFilteringUseCase: GetCampsitesByFilteringUseCase,
    private val getCampsitesByScopeUseCase: GetCampsitesByScopeUseCase,
    private val getCampsiteDetailUseCase: GetCampsiteDetailUseCase,
    private val getCampsiteReviewNotesUseCase: GetCampsiteReviewNotesUseCase,
    private val getCampsiteMessageDetailInfoUseCase: GetCampsiteMessageDetailInfoUseCase,
    private val writeReviewUseCase: WriteReviewUseCase,
    private val deleteReviewUseCase: DeleteReviewUseCase,
    private val scrapCampsiteUseCase: ScrapCampsiteUseCase
) : ViewModel() {

    private val _campsiteListData: MutableLiveData<List<CampsiteBriefInfo>?> = MutableLiveData()
    val campsiteListData: LiveData<List<CampsiteBriefInfo>?> = _campsiteListData

    private val _campsiteData: MutableLiveData<CampsiteDetailInfo?> = MutableLiveData()
    val campsiteData: LiveData<CampsiteDetailInfo?> = _campsiteData

    private val _campsiteNoteList: MutableLiveData<ArrayList<CampsiteNoteBriefInfo>?> =
        MutableLiveData()
    val campsiteNoteList: LiveData<ArrayList<CampsiteNoteBriefInfo>?> = _campsiteNoteList

    private val _campsiteMessageDetailInfo: MutableLiveData<CampsiteMessageDetailInfo?> =
        MutableLiveData()
    val campsiteMessageDetailInfo: LiveData<CampsiteMessageDetailInfo?> = _campsiteMessageDetailInfo

    private val _stateBehaviorArea: MutableLiveData<Boolean> = MutableLiveData(false)
    val stateBehaviorArea: LiveData<Boolean> = _stateBehaviorArea

    private val _stateBehaviorFilter: MutableLiveData<Boolean> = MutableLiveData(false)
    val stateBehaviorFilter: LiveData<Boolean> = _stateBehaviorFilter

    private val _stateBehaviorList: MutableLiveData<Boolean> = MutableLiveData(false)
    val stateBehaviorList: LiveData<Boolean> = _stateBehaviorList

    private val _isSearchAgain: MutableLiveData<Boolean> = MutableLiveData(false)
    val isSearchAgain: LiveData<Boolean> = _isSearchAgain

    private val _sido: MutableLiveData<String> = MutableLiveData()
    val sido: LiveData<String> = _sido

    private val _isScraped: MutableLiveData<Boolean> = MutableLiveData(false)
    val isScraped: LiveData<Boolean> = _isScraped

    var filter: SearchFilterRequest = SearchFilterRequest()
    var areaList = arrayListOf<AreaListItem>()
    var industry: Array<String> = arrayOf()
    var facility: Array<String> = arrayOf()
    var amenity: Array<String> = arrayOf()
    var theme: Array<String> = arrayOf()
    var pet: Array<String> = arrayOf()
    var season: Array<String> = arrayOf()

    fun mapGugun(gugun: List<String>): String {
        val result = StringBuilder()
        gugun.forEachIndexed { index, item ->
            if (index == 0) result.append(item)
            else result.append(" $item")
        }
        return result.toString()
    }

    fun setStateBehaviorArea(flag: Boolean) {
        _stateBehaviorArea.value = flag
    }

    fun setStateBehaviorFilter(flag: Boolean) {
        _stateBehaviorFilter.value = flag
    }

    fun setStateBehaviorList(flag: Boolean) {
        _stateBehaviorList.value = flag
    }

    fun setSido(sido: String) {
        viewModelScope.launch {
            _sido.value = sido
        }
    }

    fun setIsSearchAgain(flag: Boolean) {
        _isSearchAgain.value = flag
    }

    fun setAreaList(context: Context) {
        val area = listOf(
            "gangwon",
            "gyeonggi",
            "gyeongsang_south",
            "gyeongsang_north",
            "gwangju",
            "daegu",
            "daejeon",
            "busan",
            "seoul",
            "sejong",
            "ulsan",
            "incheon",
            "jeonla_south",
            "jeonla_north",
            "jeju",
            "chungcheong_south",
            "chungcheong_north"
        )

        area.forEach { sidoNameId ->
            val resId =
                context.resources.getIdentifier("area_$sidoNameId", "array", context.packageName)
            val stringArray = context.resources.getStringArray(resId)
            val sidoName = stringArray[0]
            val campsiteCountAll = stringArray[1].toInt()
            val gugunList = arrayListOf<GugunItem>()

            for (i in 2 until stringArray.size) {
                gugunList.add(
                    GugunItem(
                        gugun = stringArray[i].split("(")[0],
                        campsiteCount = stringArray[i].split("(")[1].split(")")[0].toInt()
                    )
                )
            }
            areaList.add(AreaListItem(sidoName, gugunList, campsiteCountAll))
        }
    }

    fun setIndustry(context: Context) {
        industry = context.resources.getStringArray(R.array.content_campsite_industry)
        facility = context.resources.getStringArray(R.array.content_campsite_facility)
        amenity = context.resources.getStringArray(R.array.content_campsite_amenity)
        theme = context.resources.getStringArray(R.array.content_campsite_theme)
        pet = context.resources.getStringArray(R.array.content_campsite_pet)
        season = context.resources.getStringArray(R.array.content_campsite_season)
    }

    fun setIsScraped(flag: Boolean) {
        _isScraped.value = flag
    }

    fun getCampsitesByFiltering(filter: SearchFilterRequest) = viewModelScope.launch {
        when (val value = getCampsitesByFilteringUseCase(filter)) {
            is Resource.Success<List<CampsiteBriefInfo>> -> {
                _campsiteListData.value = value.data
            }
            is Resource.Error -> {
                Log.e("getCampsitesByFiltering", "getCampsitesByFiltering: ${value.errorMessage}")
            }
        }
    }

    fun getCampsitesByScope(
        bottomRightLat: Double, bottomRightLng: Double, topLeftLat: Double, topLeftLng: Double
    ) = viewModelScope.launch {
        when (val value = getCampsitesByScopeUseCase(
            bottomRightLat, bottomRightLng, topLeftLat, topLeftLng
        )) {
            is Resource.Success<List<CampsiteBriefInfo>> -> {
                _campsiteListData.value = value.data
            }
            is Resource.Error -> {
                Log.e("getCampsitesByScope", "getCampsitesByScope: ${value.errorMessage}")
            }
        }
    }

    suspend fun getCampsiteDetailAsync(campsiteId: String) = viewModelScope.async {
        when (val value = getCampsiteDetailUseCase(campsiteId)) {
            is Resource.Success<CampsiteDetailInfo> -> {
                _campsiteData.value = value.data
                return@async 1
            }
            is Resource.Error -> {
                Log.e("getCampsiteDetailInfo", "getCampsiteDetailInfo: ${value.errorMessage}")
                return@async 0
            }
        }
    }.await()

    fun getCampsiteReviewNotes(
        campsiteId: String,
    ) = viewModelScope.launch {
        when (val value = getCampsiteReviewNotesUseCase(
            campsiteId,
            33.0,
            132.0,
            43.0,
            124.0,
        )) {
            is Resource.Success<List<CampsiteNoteBriefInfo>> -> {
                val arrayList = arrayListOf<CampsiteNoteBriefInfo>()

                if (campsiteNoteList.value == null)
                    _campsiteNoteList.value = arrayListOf()
                else {
                    arrayList.addAll(campsiteNoteList.value!!)
                }

                value.data.forEach {
                    if (it.messageCategory == "리뷰")
                        arrayList.add(it)
                }

                _campsiteNoteList.value = arrayList
            }
            is Resource.Error -> {
                Log.e(
                    "getCampsiteMessageBriefInfo",
                    "getCampsiteMessageBriefInfo: ${value.errorMessage}"
                )
            }
        }
    }

    suspend fun getCampsiteMessageDetailInfo(messageId: String) = viewModelScope.async {
        when (val value = getCampsiteMessageDetailInfoUseCase(messageId)) {
            is Resource.Success<CampsiteMessageDetailInfo> -> {
                _campsiteMessageDetailInfo.value = value.data
                return@async true
            }
            is Resource.Error -> {
                Log.e(
                    "getCampsiteMessageDetailInfo",
                    "getCampsiteMessageDetailInfo: ${value.errorMessage}"
                )
                return@async false
            }
        }
    }.await()

    suspend fun writeReview(review: SearchReviewRequest) = viewModelScope.async {
        when (val value = writeReviewUseCase(review)) {
            is Resource.Success<Review> -> return@async true
            is Resource.Error -> {
                Log.e("writeReview", "writeReview: ${value.errorMessage}")
                return@async false
            }
        }
    }.await()

    suspend fun deleteReview(reviewId: String) = viewModelScope.async {
        when (val value = deleteReviewUseCase(reviewId)) {
            is Resource.Success<String> -> return@async true
            is Resource.Error -> {
                Log.e("deleteReview", "deleteReview: ${value.errorMessage}")
                return@async false
            }
        }
    }.await()

    suspend fun scrapCampsite(campsiteId: String) = viewModelScope.async {
        when (val value = scrapCampsiteUseCase(campsiteId)) {
            is Resource.Success<Boolean> -> return@async value.data.toString()
            is Resource.Error -> {
                Log.e("scrapCampsite", "scrapCampsite: ${value.errorMessage}")
                return@async "error"
            }
        }
    }.await()
}