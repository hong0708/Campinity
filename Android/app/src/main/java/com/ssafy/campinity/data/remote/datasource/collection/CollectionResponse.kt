package com.ssafy.campinity.data.remote.datasource.collection

import com.google.gson.annotations.SerializedName
import com.ssafy.campinity.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.campinity.domain.entity.collection.CollectionItem

data class CollectionResponse(
    @SerializedName("campsiteName")
    val campsiteName: String,
    @SerializedName("collectionId")
    val collectionId: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("imagePath")
    val imagePath: String,
    @SerializedName("updatedAt")
    val updatedAt: String
) : DataToDomainMapper<CollectionItem> {
    override fun toDomainModel(): CollectionItem {
        return CollectionItem(campsiteName, collectionId, content, date, imagePath)
    }
}