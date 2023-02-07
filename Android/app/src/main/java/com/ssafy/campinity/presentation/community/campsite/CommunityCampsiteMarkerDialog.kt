package com.ssafy.campinity.presentation.community.campsite

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import com.ssafy.campinity.databinding.DialogWriteReviewNoteMarkerBinding
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class CommunityCampsiteMarkerDialog(
    val activity: Activity,
    private val listener: CommunityCampsiteMarkerDialogListener
) : Dialog(activity){

    private lateinit var binding: DialogWriteReviewNoteMarkerBinding
    private lateinit var mapView: MapView

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
        binding.clMapMarker.addView(mapView)

        //임의 중앙 값 찍음 후에 위치 기반으로 넘겨줘서 찍어야함
        val uLatitude = 36.1071
        val uLongitude = 128.4164
        val uNowPosition = MapPoint.mapPointWithGeoCoord(uLatitude, uLongitude)
        mapView.setMapCenterPoint(uNowPosition, true)

        binding.apply {
            tvCancelEventNoteMarker.setOnClickListener {
                dismiss()
            }
            tvMakeEventNoteMarker.setOnClickListener {
                listener.onSubmitButtonClicked(
                    mapView.mapCenterPoint.mapPointGeoCoord.longitude,
                    mapView.mapCenterPoint.mapPointGeoCoord.latitude
                )
                dismiss()
            }
        }
    }
}