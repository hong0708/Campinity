package com.ssafy.campinity.common.util

import android.content.Context
import android.util.TypedValue

fun Int.dp(context: Context): Int { //함수 이름도 직관적으로 보이기 위해 dp()로 바꿨습니다.
    val metrics = context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), metrics).toInt()
}