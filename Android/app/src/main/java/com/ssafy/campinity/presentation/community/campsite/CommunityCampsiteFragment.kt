package com.ssafy.campinity.presentation.community.campsite

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.CustomDialog
import com.ssafy.campinity.common.util.CustomDialogInterface
import com.ssafy.campinity.databinding.FragmentCommunityCampsiteBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

@AndroidEntryPoint
class CommunityCampsiteFragment :
    BaseFragment<FragmentCommunityCampsiteBinding>(R.layout.fragment_community_campsite),
    CustomDialogInterface {

    private lateinit var viewList: List<View>
    private lateinit var fabList: List<ConstraintLayout>
    private lateinit var mapView: MapView
    private val moveValues: List<Float> = listOf(800f, 600f, 400f, 200f)
    private val communityCampsiteTitleListAdapter by lazy {
        CommunityCampsiteTitleListAdapter(this::getCampsiteTitle)
    }
    private val communityCampsiteViewModel by activityViewModels<CommunityCampsiteViewModel>()
    private var isFabOpen = false
    private var isTracking = false

    override fun initView() {
        initListener()
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        mapView = MapView(activity)

        val listener = CommunityMapViewEventListener()
        mapView.setMapViewEventListener(listener)

        binding.clCommunityMap.addView(mapView)
    }

    override fun onPause() {
        super.onPause()
        binding.clCommunityMap.removeView(mapView)
    }

    override fun onFinishButton() {
        onDestroyView()
    }

    private fun initListener() {
        binding.apply {

            fabUserLocation.setOnClickListener {
                if (!isTracking) {
                    Toast.makeText(requireContext(), "사용자의 위치를 추적합니다.", Toast.LENGTH_SHORT).show()
                    mapView.currentLocationTrackingMode =
                            // TrackingModeOnWithoutHeading -> 이동하면 지도 뷰가 따라옴
                            // TrackingModeOnWithoutHeadingWithoutMapMoving -> 내 위치는 움직이지만 지도 뷰는 멈춤
                        MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading

                    // 내 위치를 나타내는 뷰 변경경
                    /*mapView.setCustomCurrentLocationMarkerImage(
                         R.drawable.ic_community_campsite_marker,
                         MapPOIItem.ImageOffset,
                         anchorPointOffset
                     )*/

                } else {
                    Toast.makeText(requireContext(), "위치 추적을 멈춥니다.", Toast.LENGTH_SHORT).show()
                    mapView.currentLocationTrackingMode =
                            // TrackingModeOff -> 지도 뷰에서 내가 안움직임 아마 안쓸듯
                        MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeadingWithoutMapMoving
                }
                isTracking = !isTracking

                Log.d("testlistener", "initListener: ${mapView.zoomLevel}")
            }
            tvSearchCurrentLocation.setOnClickListener {
                val center = mapView.mapCenterPoint.mapPointGeoCoord
                val bottomLeft = mapView.mapPointBounds.bottomLeft.mapPointGeoCoord
                val topRight = mapView.mapPointBounds.topRight.mapPointGeoCoord

                mapView.mapCenterPoint.mapPointScreenLocation
                val level = mapView.zoomLevel
                Log.d(
                    "map data",
                    "initListener: 중심점 : x = ${center.latitude} , y = ${center.longitude} , 줌레벨 : $level"
                )
                Log.d(
                    "map data",
                    "바운드 출력: bottomLeft.longitude ${bottomLeft.longitude} bottomLeft.latitude ${bottomLeft.latitude}"
                )
                Log.d(
                    "map data",
                    "바운드 출력: topRight.longitude ${topRight.longitude} topRight.latitude ${topRight.latitude}"
                )

                initMapView()
            }





            fabHelp.setOnClickListener {
            }

            fabGetHelp.setOnClickListener {
                CustomDialog(
                    requireContext(),
                    this@CommunityCampsiteFragment,
                    R.layout.dialog_write_event_note,
                    R.id.iv_cancel_help_dialog,
                    R.id.btn_make_note_help
                ).show()
            }

            fabReview.setOnClickListener {
                CustomDialog(
                    requireContext(),
                    this@CommunityCampsiteFragment,
                    R.layout.dialog_write_review_note,
                    R.id.iv_close_write_review_note_dialog,
                    R.id.tv_make_review
                ).show()
            }

            fabFreeNote.setOnClickListener {
                CustomDialog(
                    requireContext(),
                    this@CommunityCampsiteFragment,
                    R.layout.dialog_write_free_note,
                    R.id.iv_close_write_review_note_dialog,
                    R.id.tv_make_review
                ).show()
            }

            // SlidingUpPanel
            val slidePanel = binding.slCommunityFrame
            // 이벤트 리스너 추가
            slidePanel.addPanelSlideListener(PanelEventListener())

            // 패널 열고 닫기
            clCampsiteCondition.setOnClickListener {
                val state = slidePanel.panelState
                // 닫힌 상태일 경우 열기
                if (state == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
                }
            }

            viewList =
                listOf(tvFabHelp, tvFabGetHelp, tvFabReview, tvFabFreeNote, clMapBackSite)
            fabList = listOf(clFabHelp, clFabGetHelp, clFabReview, clFabFreeNote)

            clMapBackSite.setOnClickListener {
                for (i in fabList) {
                    returnFab(i)
                }
                for (i in viewList) {
                    eraseTv(i)
                }
            }

            fabMain.setOnClickListener {
                if (isFabOpen) {
                    for (i in fabList) {
                        returnFab(i)
                    }
                    for (i in viewList) {
                        eraseTv(i)
                    }
                } else {
                    for (i in 0..3) {
                        moveFab(fabList[i], moveValues[i])
                    }
                    for (i in viewList) {
                        writeTv(i)
                    }
                }
                isFabOpen = !isFabOpen
            }
        }
    }

    private fun eraseTv(view: View) {
        view.visibility = View.GONE
    }

    private fun writeTv(view: View) {
        view.visibility = View.VISIBLE
    }

    private fun returnFab(clFab: ConstraintLayout) {
        ObjectAnimator.ofFloat(clFab, "translationY", 0f).apply { start() }
    }

    private fun moveFab(clFab: ConstraintLayout, moveValue: Float) {
        ObjectAnimator.ofFloat(clFab, "translationY", -moveValue).apply { start() }
    }

    private fun initRecyclerView() {
        binding.rvCampsiteList.apply {
            adapter = communityCampsiteTitleListAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private fun getCampsiteTitle(campsiteId: String) {
        communityCampsiteViewModel.getCampsiteTitle(campsiteId)
    }

    // 이벤트 리스너
    inner class PanelEventListener : SlidingUpPanelLayout.PanelSlideListener {
        // 패널이 슬라이드 중일 때
        override fun onPanelSlide(panel: View?, slideOffset: Float) {}

        // 패널의 상태가 변했을 때
        override fun onPanelStateChanged(
            panel: View?,
            previousState: SlidingUpPanelLayout.PanelState?,
            newState: SlidingUpPanelLayout.PanelState?
        ) {
        }
    }

    /*// 위치추적 시작
    private fun startTracking() {
        mapView.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading
    }

    // 위치추적 중지
    private fun stopTracking() {
        mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOff
    }*/

    @SuppressLint("MissingPermission")
    private fun initMapView() {
        // 마커 찍는 부분
        val lm: LocationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
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
}