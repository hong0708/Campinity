package com.ssafy.campinity.domain.entity.search

data class CampsiteDetailInfo(
    val campsiteId: String,
    val amenities: List<String>,
    val caravanFacilities: List<String>,
    val glampingFacilities: List<String>,
    val openSeasons: List<String>,
    val themes: List<String>,
    val industries: List<String>,
    val isScraped: Boolean,
    val campsiteName: String,
    val address: String,
    val phoneNumber: String,
    val homepage: String,
    val reserveType: String,
    val intro: String,
    val lineIntro: String,
    val experienceProgram: String,
    val subFacilityEtc: String,
    val dayOperation: String,
    val allowAnimal: String,
    val images: List<String>,
    val reviews: List<Review>,
    val total_rate: Double
)