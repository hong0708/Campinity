package com.ssafy.campinity.presentation.community.campsite

import android.util.Log
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

// 추후 모션 인식에 따른 동작 설정 필요
class CommunityMapViewEventListener: MapView.MapViewEventListener {
    val TAG = "MapViewEventListener"
    
    override fun onMapViewInitialized(p0: MapView?) {
        Log.d(TAG, "onMapViewInitialized: ")
    }

    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {
        Log.d(TAG, "onMapViewCenterPointMoved: ")
    }

    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {
        Log.d(TAG, "onMapViewZoomLevelChanged: ")
    }

    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {
        Log.d(TAG, "onMapViewSingleTapped: ")
    }

    override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {
        Log.d(TAG, "onMapViewDoubleTapped: ")
    }

    override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {
        Log.d(TAG, "onMapViewLongPressed: ")
    }

    override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {
        Log.d(TAG, "onMapViewDragStarted: ")
    }

    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {
        Log.d(TAG, "onMapViewDragEnded: ")
    }

    override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {
        Log.d(TAG, "onMapViewMoveFinished: ")
    }
}