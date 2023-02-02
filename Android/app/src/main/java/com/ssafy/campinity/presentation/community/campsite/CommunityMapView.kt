package com.ssafy.campinity.presentation.community.campsite


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.util.Log
import com.ssafy.campinity.R
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

@SuppressLint("ViewConstructor")
class CommunityMapView(activity: Activity, context: Context?) : MapView(context) {

    private val mapView = MapView(activity)
    private val markerImage: Int = R.drawable.ic_community_campsite_marker

    // 마커를 그리는 함수
    private fun markers(markerLocationList: List<Location>) {
        for (i in markerLocationList) {
            val markerPosition = MapPoint.mapPointWithGeoCoord(i.latitude, i.longitude)
            val marker = MapPOIItem()
            marker.itemName = "현 위치"
            marker.mapPoint = markerPosition
            marker.markerType = MapPOIItem.MarkerType.CustomImage
            marker.customImageResourceId = R.drawable.ic_community_campsite_marker
            marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin
            mapView.addPOIItem(marker)
        }
    }

    @SuppressLint("MissingPermission")
    private fun initMapView(activity: Activity) {
        // 마커 찍는 부분
        val lm: LocationManager =
            activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val userNowLocation: Location? =
            lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        //위도 , 경도
        val uLatitude = userNowLocation?.latitude
        val uLongitude = userNowLocation?.longitude
        val uNowPosition = MapPoint.mapPointWithGeoCoord(uLatitude!!, uLongitude!!)

        // 현 위치에 마커 찍기
        val marker = MapPOIItem()
        marker.itemName = "현 위치"
        marker.mapPoint = uNowPosition
        marker.markerType = MapPOIItem.MarkerType.CustomImage
        marker.customImageResourceId = R.drawable.ic_community_campsite_marker
        marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin
        mapView.addPOIItem(marker)
    }

    /*override fun onMapViewZoomLevelChanged(zoomLevel: Int) {
        super.onMapViewZoomLevelChanged(zoomLevel)
    }*/

    /*private fun onMapViewZoomLevelChanged(mapView:MapView,zoomLevel: Int) {
        Log.d(TAG, "onMapViewZoomLevelChanged: ")
    }*/
}