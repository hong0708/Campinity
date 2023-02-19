package com.ssafy.campinity.presentation.community.campsite

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationClient {
    fun getLocationUpdates(time: Long): Flow<Location>

    class LocationEcxeption(message: String) : java.lang.Exception()
}