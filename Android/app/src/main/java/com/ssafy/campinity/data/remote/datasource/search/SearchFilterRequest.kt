package com.ssafy.campinity.data.remote.datasource.search

import com.google.gson.annotations.SerializedName

data class SearchFilterRequest(
    @SerializedName("allowAnimal")
    var allowAnimal: String = "",
    @SerializedName("amenity")
    var amenity: String = "",
    @SerializedName("doName")
    var doName: String = "",
    @SerializedName("fclty")
    var fclty: String = "",
    @SerializedName("industry")
    var industry: String = "",
    @SerializedName("keyword")
    var keyword: String = "",
    @SerializedName("openSeason")
    var openSeason: String = "",
    @SerializedName("sigunguName")
    var sigunguName: String = "",
    @SerializedName("theme")
    var theme: String = ""
)