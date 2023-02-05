package com.ssafy.campinity.data.remote.datasource.search

import com.google.gson.annotations.SerializedName
import com.ssafy.campinity.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.campinity.domain.entity.search.CampsiteDetailInfo
import com.ssafy.campinity.domain.entity.search.Review

data class SearchDetailResponse(
    @SerializedName("address")
    val address: String,
    @SerializedName("allowAnimal")
    val allowAnimal: String,
    @SerializedName("amenities")
    val amenities: List<Int>,
    @SerializedName("campName")
    val campName: String,
    @SerializedName("campsiteId")
    val campsiteId: String,
    @SerializedName("caravanfclties")
    val caravanfclties: List<Int>,
    @SerializedName("dayOperation")
    val dayOperation: String,
    @SerializedName("experienceProgram")
    val experienceProgram: String,
    @SerializedName("glampfclties")
    val glampfclties: List<Int>,
    @SerializedName("homepage")
    val homepage: String,
    @SerializedName("images")
    val images: List<String>,
    @SerializedName("industries")
    val industries: List<String>,
    @SerializedName("intro")
    val intro: String,
    @SerializedName("isScraped")
    val isScraped: Boolean,
    @SerializedName("lineIntro")
    val lineIntro: String,
    @SerializedName("operSeasons")
    val operSeasons: List<String>,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("reserveType")
    val reserveType: String,
    @SerializedName("reviews")
    val review: List<Review>,
    @SerializedName("subFacilityEtc")
    val subFacilityEtc: String,
    @SerializedName("themes")
    val themes: List<Int>,
    @SerializedName("total_rate")
    val totalRate: Double
) : DataToDomainMapper<CampsiteDetailInfo> {
    override fun toDomainModel(): CampsiteDetailInfo = CampsiteDetailInfo(
        address = address,
        allowAnimal = allowAnimal,
        amenities = amenities,
        campsiteName = campName,
        campsiteId = campsiteId,
        caravanFacilities = caravanfclties,
        dayOperation = dayOperation,
        experienceProgram = experienceProgram,
        glampingFacilities = glampfclties,
        homepage = homepage,
        images = images,
        industries = industries,
        intro = intro,
        isScraped = isScraped,
        lineIntro = lineIntro,
        openSeasons = operSeasons,
        phoneNumber = phoneNumber,
        reserveType = reserveType,
        reviews = review,
        subFacilityEtc = subFacilityEtc,
        themes = themes,
        totalRate = totalRate
    )
}