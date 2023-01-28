package com.ssafy.campinity.common.util

import android.content.Context

fun Int.dp(context: Context): Int {
    return (this / context.resources.displayMetrics.density).toInt()
}

fun Int.px(context: Context): Int {
    return (this * context.resources.displayMetrics.density).toInt()
}

fun getDeviceWidthPx(context: Context): Int {
    return context.resources.displayMetrics.widthPixels
}

fun getDeviceHeightPx(context: Context): Int {
    return context.resources.displayMetrics.heightPixels
}