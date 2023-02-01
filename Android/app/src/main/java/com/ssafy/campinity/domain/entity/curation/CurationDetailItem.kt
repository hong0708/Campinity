package com.ssafy.campinity.domain.entity.curation

data class CurationDetailItem(
    val content: String,
    val imagePaths: List<String>,
    val isScraped: Boolean,
    val title: String
)