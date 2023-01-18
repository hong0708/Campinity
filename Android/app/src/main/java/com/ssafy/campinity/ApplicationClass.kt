package com.ssafy.campinity

import android.app.Application
import com.ssafy.campinity.data.local.datasource.SharedPreferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationClass: Application() {

    companion object {
        lateinit var preferences: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()
        preferences = SharedPreferences(applicationContext)
    }
}