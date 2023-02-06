package com.ssafy.campinity.common.util

import android.annotation.SuppressLint
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

@SuppressLint("InternalInsetResource")
fun getStatusBarHeight(context: Context): Int {
    val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
    return if (resourceId > 0) {
        context.resources.getDimensionPixelSize(resourceId)
    } else {
        0
    }
}

@SuppressLint("InternalInsetResource")
fun getNaviBarHeight(context: Context): Int {
    val resourceId: Int =
        context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
    return if (resourceId > 0) {
        context.resources.getDimensionPixelSize(resourceId)
    } else {
        0
    }
}

fun List<*>.toString(regex: String): String {
    val stringBuilder = StringBuilder()

    this.forEachIndexed { index, d ->
        if (index == 0) {
            stringBuilder.append(d)
        } else {
            stringBuilder.append("$regex$d")
        }
    }

    return stringBuilder.toString()
}