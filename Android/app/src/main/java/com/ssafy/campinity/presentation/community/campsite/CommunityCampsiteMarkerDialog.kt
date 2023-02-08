package com.ssafy.campinity.presentation.community.campsite

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import com.ssafy.campinity.ApplicationClass
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.DialogWriteReviewNoteMarkerBinding
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class CommunityCampsiteMarkerDialog(
    val activity: Activity,
    private val listener: CommunityCampsiteMarkerDialogListener
) : Dialog(activity),
    MapView.MapViewEventListener {

    private lateinit var binding: DialogWriteReviewNoteMarkerBinding
    private lateinit var mapView: MapView
    private var marker = MapPOIItem()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogWriteReviewNoteMarkerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(true)
        setCancelable(true)

        mapView = MapView(activity)

        mapView.setZoomLevel(2, true)
        mapView.setMapViewEventListener(this)
        binding.clMapMarker.addView(mapView)

        val uNowPosition = MapPoint.mapPointWithGeoCoord(
            ApplicationClass.preferences.userRecentCampsiteLatitude!!.toDouble(),
            ApplicationClass.preferences.userRecentCampsiteLongitude!!.toDouble()
        )
        mapView.setMapCenterPoint(uNowPosition, true)

        binding.apply {
            tvCancelEventNoteMarker.setOnClickListener {
                dismiss()
            }
            tvMakeEventNoteMarker.setOnClickListener {
                if (marker.mapPoint.mapPointGeoCoord.longitude != 0.0) {
                    listener.onSubmitButtonClicked(
                        marker.mapPoint.mapPointGeoCoord.longitude,
                        marker.mapPoint.mapPointGeoCoord.latitude
                    )
                }
                dismiss()
            }
        }
    }

    override fun onMapViewInitialized(p0: MapView?) {}

    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {}

    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {}

    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {
        mapView.removeAllPOIItems()

        val markerPosition =
            MapPoint.mapPointWithGeoCoord(
                p1?.mapPointGeoCoord!!.latitude,
                p1.mapPointGeoCoord!!.longitude
            )

        marker.apply {
            itemName = "마커 위치"
            mapPoint = markerPosition
            markerType = MapPOIItem.MarkerType.CustomImage
            customImageResourceId = R.drawable.ic_location_marker_pin
            isCustomImageAutoscale = true      // 커스텀 마커 이미지 크기 자동 조정

            tag = 3
        }
        mapView.addPOIItem(marker)
    }

    override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {}

    override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {}

    override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {}

    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {}

    override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {}
}