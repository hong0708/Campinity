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

fun Context.showToastMessage(message: String) {
    CustomToast.createToast(this, message).show()
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