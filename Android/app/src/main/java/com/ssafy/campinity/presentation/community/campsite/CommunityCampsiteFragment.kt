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
import com.ssafy.campinity.ApplicationClass
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.CustomDialog
import com.ssafy.campinity.common.util.CustomDialogInterface
import com.ssafy.campinity.databinding.FragmentCommunityCampsiteBinding
import com.ssafy.campinity.domain.entity.community.CampsiteMessageBriefInfo
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

@AndroidEntryPoint
class CommunityCampsiteFragment :
    BaseFragment<FragmentCommunityCampsiteBinding>(R.layout.fragment_community_campsite),
    CustomDialogInterface,
    MapViewMarkerEventListener {

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
        // 추후 최근 검색으로 초기화 작업 추가 진행
        if (ApplicationClass.preferences.userRecentCampsiteName == null) {
            binding.tvCampsiteCondition.text = "미 지정"
        } else {
            binding.tvCampsiteCondition.text = ApplicationClass.preferences.userRecentCampsiteName
        }

        mapView = MapView(activity)

        val listener = CommunityMapViewEventListener(this)
        mapView.setMapViewEventListener(listener)
        mapView.setZoomLevel(1, true)
        binding.clCommunityMap.addView(mapView)
        //initMapView()
    }

    override fun onPause() {
        super.onPause()
        binding.clCommunityMap.removeView(mapView)
    }

    override fun onFinishButton() {
        onDestroyView()
    }

    override fun move() {
        //ApplicationClass.preferences.userRecentCampsiteId

        communityCampsiteViewModel.getCampsiteMessageBriefInfoByScope(
            mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
            mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude,
            ApplicationClass.preferences.userRecentCampsiteId.toString(),
            mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude,
            mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude
        )
    }

    override fun zoomLevelChanged() {
        communityCampsiteViewModel.getCampsiteMessageBriefInfoByScope(
            mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
            mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude,
            ApplicationClass.preferences.userRecentCampsiteId.toString(),
            mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude,
            mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude
        )
    }

    @SuppressLint("MissingPermission")
    private fun initMapView() {

        //mapView.setMapCenterPoint()
        /*mapView.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeadingWithoutMapMoving*/

        // 현재 위치 기준으로 맵 이동
        val lm: LocationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val userNowLocation: Location? =
            lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        //위도 , 경도
        val uLatitude = userNowLocation?.latitude
        val uLongitude = userNowLocation?.longitude
        val uNowPosition = MapPoint.mapPointWithGeoCoord(uLatitude!!, uLongitude!!)
        mapView.setMapCenterPoint(uNowPosition, true)

        // 현 위치에 마커 찍기
        /*val marker = MapPOIItem()

        marker.apply {
            itemName = "현 위치"
            mapPoint = uNowPosition
            markerType = MapPOIItem.MarkerType.CustomImage
            customImageResourceId = R.drawable.ic_community_campsite_marker
            selectedMarkerType = MapPOIItem.MarkerType.RedPin
        }
        mapView.addPOIItem(marker)*/
    }

    private fun initRecyclerView() {

        binding.rvCampsiteList.apply {
            adapter = communityCampsiteTitleListAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }

        communityCampsiteViewModel.campsiteBriefInfo.observe(viewLifecycleOwner) { response ->
            response.let { communityCampsiteTitleListAdapter.setCampsiteBriefInfo(it) }
        }
    }

    @SuppressLint("MissingPermission")
    private fun initListener() {
        binding.apply {

            fabUserLocation.setOnClickListener {
                if (isTracking) {
                    Toast.makeText(requireContext(), "위치 추적을 멈춥니다.", Toast.LENGTH_SHORT).show()
                    mapView.currentLocationTrackingMode =
                        MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeadingWithoutMapMoving
                } else {
                    Toast.makeText(requireContext(), "사용자의 위치를 추적합니다.", Toast.LENGTH_SHORT).show()
                    mapView.currentLocationTrackingMode =
                        MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading
                }
                isTracking = !isTracking
            }

            clSearchByUserLocation.setOnClickListener {
                //추적 시작
                Toast.makeText(requireContext(), "사용자의 위치와 지도 기준으로 검색 합니다.", Toast.LENGTH_SHORT)
                    .show()
                mapView.currentLocationTrackingMode =
                    MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeadingWithoutMapMoving
                if (isTracking) {
                    isTracking = false
                }
                // 현재 내위치 기준으로 맵 포인트 가운데로 옮기기
                // 추후 맵 레벨 바꿔서 검색 진행
                val lm: LocationManager =
                    requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val userNowLocation: Location? =
                    lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                //위도 , 경도
                val uLatitude = userNowLocation?.latitude
                val uLongitude = userNowLocation?.longitude
                val uNowPosition = MapPoint.mapPointWithGeoCoord(uLatitude!!, uLongitude!!)
                mapView.setMapCenterPoint(uNowPosition, true)

                mapView.mapCenterPoint.mapPointScreenLocation

                communityCampsiteViewModel.getCampsiteBriefInfoByUserLocation(
                    mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
                    mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude,
                    mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude,
                    mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude
                )

                Log.d(
                    "abcde",
                    "initListener: ${mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude}  " +
                            "${mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude}" +
                            "${mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude}  " +
                            "${mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude}  "
                )
            }

            tvSearchCampsiteByName.setOnClickListener {
                communityCampsiteViewModel.getCampsiteBriefInfoByCampName(etInputCampsiteName.text.toString())
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


    private fun getCampsiteTitle(campsiteId: String, campsiteName: String?) {
        // 해당 캠핑장에 대한 아이디를 넘겨줘서 맵에 마커 그리기
        // ApplicationClass.preferences.
        binding.tvCampsiteCondition.text = campsiteName
        ApplicationClass.preferences.userRecentCampsiteId = campsiteId
        ApplicationClass.preferences.userRecentCampsiteName = campsiteName

        binding.slCommunityFrame.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED

        // 싸피 캠핑장 기준으로 맵 가운데 지점 옮기기
        // 추후 받아온 위 경도 활용
        val uLatitude = 36.1071
        val uLongitude = 128.4164
        val uNowPosition = MapPoint.mapPointWithGeoCoord(uLatitude, uLongitude)
        mapView.setMapCenterPoint(uNowPosition, true)

        // 쪽지 그릴 리스트 가져오기
        communityCampsiteViewModel.getCampsiteMessageBriefInfoByScope(
            mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
            mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude,
            campsiteId,
            mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude,
            mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude
        )

        communityCampsiteViewModel.campsiteMessageBriefInfo.observe(viewLifecycleOwner) { response ->
            drawMarkers(response)
        }

        /*// 리스너 다시 달기
        val listener = CommunityMapViewEventListener(this@CommunityCampsiteFragment)
        mapView.setMapViewEventListener(listener)*/
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
            /*Log.d("MapViewEventListener:", "onPanelStateChanged: ")
            val listener = CommunityMapViewEventListener(this@CommunityCampsiteFragment)
            mapView.setMapViewEventListener(listener)*/
        }
    }


    // 마커를 그리는 함수
    private fun drawMarkers(markerLocationList: List<CampsiteMessageBriefInfo>) {
        for (i in markerLocationList) {
            val markerPosition =
                MapPoint.mapPointWithGeoCoord(i.latitude.toDouble(), i.longitude.toDouble())
            val marker = MapPOIItem()
            marker.apply {
                itemName = "현 위치"
                mapPoint = markerPosition
                markerType = MapPOIItem.MarkerType.CustomImage
                customImageResourceId = R.drawable.ic_community_campsite_marker
                selectedMarkerType = MapPOIItem.MarkerType.RedPin
            }
            mapView.addPOIItem(marker)
        }
    }



    // 내 위치 권한 허용 함수들


}