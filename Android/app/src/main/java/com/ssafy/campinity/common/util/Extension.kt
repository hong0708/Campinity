package com.ssafy.campinity.common.util

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue

fun Int.dp(context: Context): Int {
    val metrics = context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), metrics).toInt()
}

fun getDeviceWidthDp(context: Context): Float {
    with(context.resources.displayMetrics) {
        return this.widthPixels / (this.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}

fun getDeviceHeightDp(context: Context): Float {
    with(context.resources.displayMetrics) {
        return this.heightPixels / (this.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}