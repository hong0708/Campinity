package com.ssafy.campinity.data.local.datasource

import android.content.Context

class SharedPreferences(context: Context) {

    private val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    var accessToken: String?
        get() = prefs.getString("accessToken", null)
        set(value) = prefs.edit().putString("accessToken", value).apply()

    var refreshToken: String?
        get() = prefs.getString("refreshToken", null)
        set(value) = prefs.edit().putString("refreshToken", value).apply()

    var isLoggedIn: Boolean
        get() = prefs.getBoolean("isLoggedIn", false)
        set(value) = prefs.edit().putBoolean("isLoggedIn", value).apply()

    var userRecentCampsiteId: String?
        get() = prefs.getString("userRecentCampsite", null)
        set(value) = prefs.edit().putString("userRecentCampsite", value).apply()

    var userRecentCampsiteName: String?
        get() = prefs.getString("userRecentCampsiteName", null)
        set(value) = prefs.edit().putString("userRecentCampsiteName", value).apply()

    fun clearPreferences() {
        prefs.edit().clear().apply()
    }
}