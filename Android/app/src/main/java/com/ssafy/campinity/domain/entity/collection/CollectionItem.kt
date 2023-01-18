package com.ssafy.campinity.domain.entity.collection

data class CollectionItem(
    val collectionId: String = "",
    val img: Int,
    val title: String,
    val date: String,
    val content: String
)