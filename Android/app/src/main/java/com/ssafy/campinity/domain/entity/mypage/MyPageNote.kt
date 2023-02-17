package com.ssafy.campinity.domain.entity.mypage

import com.ssafy.campinity.domain.entity.community.CampsiteMessageDetailInfo

data class MyPageNote(
    val myETCMessages: List<CampsiteMessageDetailInfo>,
    val myReviewMessages: List<CampsiteMessageDetailInfo>
)
