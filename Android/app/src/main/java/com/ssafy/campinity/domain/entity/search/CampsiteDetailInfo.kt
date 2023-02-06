package com.ssafy.campinity.domain.entity.search

data class CampsiteDetailInfo(
    val campsiteId: String,
    val amenities: List<Int>,
    val caravanFacilities: List<Int>,
    val glampingFacilities: List<Int>,
    val openSeasons: List<String>,
    val themes: List<Int>,
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
    val totalRate: Double
) {
    override fun toString(): String {
        return "CampsiteDetailInfo(campsiteId='$campsiteId', amenities=$amenities, caravanFacilities=$caravanFacilities, glampingFacilities=$glampingFacilities, openSeasons=$openSeasons, themes=$themes, industries=$industries, isScraped=$isScraped, campsiteName='$campsiteName', address='$address', phoneNumber='$phoneNumber', homepage='$homepage', reserveType='$reserveType', intro='$intro', lineIntro='$lineIntro', experienceProgram='$experienceProgram', subFacilityEtc='$subFacilityEtc', dayOperation='$dayOperation', allowAnimal='$allowAnimal', images=$images, reviews=$reviews, totalRate=$totalRate)"
    }
}