package com.sssafy.campinity.common.util

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun Context.isGranted(vararg permissionName: String): Boolean {
    permissionName.forEach {
        if (ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_DENIED) {
            return false
        }
    }
    return true
}
