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

    var fcmToken: String?
        get() = prefs.getString("fcmToken", null)
        set(value) = prefs.edit().putString("fcmToken", value).apply()

    var isLoggedIn: Boolean
        get() = prefs.getBoolean("isLoggedIn", false)
        set(value) = prefs.edit().putBoolean("isLoggedIn", value).apply()

    var userRecentCampsiteId: String?
        get() = prefs.getString("userRecentCampsite", null)
        set(value) = prefs.edit().putString("userRecentCampsite", value).apply()

    var userRecentCampsiteName: String?
        get() = prefs.getString("userRecentCampsiteName", null)
        set(value) = prefs.edit().putString("userRecentCampsiteName", value).apply()

    var userRecentCampsiteLatitude: String?
        get() = prefs.getString("userRecentCampsiteLatitude", null)
        set(value) = prefs.edit().putString("userRecentCampsiteLatitude", value).apply()

    var userRecentCampsiteLongitude: String?
        get() = prefs.getString("userRecentCampsiteLongitude", null)
        set(value) = prefs.edit().putString("userRecentCampsiteLongitude", value).apply()

    var userRecentCampsiteAddress: String?
        get() = prefs.getString("userRecentCampsiteAddress", null)
        set(value) = prefs.edit().putString("userRecentCampsiteAddress", value).apply()

    var nickname: String?
        get() = prefs.getString("nickname", null)
        set(value) = prefs.edit().putString("nickname", value).apply()

    var isSubScribing: Boolean
        get() = prefs.getBoolean("isSubscribing", false)
        set(value) = prefs.edit().putBoolean("isSubscribing", value).apply()

    var helpContent: String?
        get() = prefs.getString("helpContent", null)
        set(value) = prefs.edit().putString("helpContent", value).apply()

    var fcmMessageId: String?
        get() = prefs.getString("fcmMessageId", null)
        set(value) = prefs.edit().putString("fcmMessageId", value).apply()

    var recentUserLat: String?
        get() = prefs.getString("recentUserLat", null)
        set(value) = prefs.edit().putString("recentUserLat", value).apply()

    var recentUserLng: String?
        get() = prefs.getString("recentUserLng", null)
        set(value) = prefs.edit().putString("recentUserLng", value).apply()

    var isUserIn: String?
        get() = prefs.getString("isUserIn", null)
        set(value) = prefs.edit().putString("isUserIn", value).apply()

    fun clearPreferences() {
        prefs.edit().clear().apply()
    }
}