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
import okhttp3.MultipartBody

@AndroidEntryPoint
class CommunityCampsiteFragment :
    BaseFragment<FragmentCommunityCampsiteBinding>(R.layout.fragment_community_campsite),
    CustomDialogInterface,
    CommunityCampsiteFreeReviewDialogInterface,
    MapViewMarkerEventListener,
    MapView.MapViewEventListener,
    MapView.POIItemEventListener,
    CommunityCampsiteFreeReviewDetailDialogInterface {

    val TAG = "MapViewEventListener"

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

        /*val listener = CommunityMapViewEventListener(this)
        mapView.setMapViewEventListener(listener)*/
        mapView.setMapViewEventListener(this)
        mapView.setPOIItemEventListener(this)
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

    override fun createFreeReviewNote(
        messageCategory: String,
        file: MultipartBody.Part?,
        latitude: Double,
        content: String,
        longitude: Double,
    ) {
        //communityCampsiteViewModel
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
                /*CustomDialog(
                    requireContext(),
                    this@CommunityCampsiteFragment,
                    R.layout.dialog_write_review_note,
                    R.id.iv_close_write_review_note_dialog,
                    R.id.tv_make_review
                ).show()*/

                // 캠핑장 설정 먼저 해야함 후에 제한 두기
                navigate(
                    CommunityCampsiteFragmentDirections
                        .actionCommunityCampsiteFragmentToCommunityCampsiteDialogFragment(
                            "리뷰 쪽지",
                            ApplicationClass.preferences.userRecentCampsiteId.toString()
                        )
                )
            }

            fabFreeNote.setOnClickListener {
                /*CustomDialog(
                    requireContext(),
                    this@CommunityCampsiteFragment,
                    R.layout.dialog_write_free_note,
                    R.id.iv_close_write_review_note_dialog,
                    R.id.tv_make_review
                ).show()*/
                navigate(
                    CommunityCampsiteFragmentDirections
                        .actionCommunityCampsiteFragmentToCommunityCampsiteDialogFragment(
                            "자유 쪽지",
                            ApplicationClass.preferences.userRecentCampsiteId.toString()
                        )
                )
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


    private fun getCampsiteTitle(
        campsiteId: String,
        campsiteName: String?,
        longitude: String,
        latitude: String
    ) {
        // 해당 캠핑장에 대한 아이디를 넘겨줘서 맵에 마커 그리기
        // ApplicationClass.preferences.
        binding.tvCampsiteCondition.text = campsiteName
        ApplicationClass.preferences.userRecentCampsiteId = campsiteId
        ApplicationClass.preferences.userRecentCampsiteName = campsiteName

        binding.slCommunityFrame.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED

        // 싸피 캠핑장 기준으로 맵 가운데 지점 옮기기
        // 추후 받아온 위 경도 활용
        val uLatitude = latitude.toDouble()
        val uLongitude = longitude.toDouble()
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
            Log.d(TAG, "getCampsiteTitle: ${response.toString()}")
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
        }
    }


    // 마커를 그리는 함수
    private fun drawMarkers(markerLocationList: List<CampsiteMessageBriefInfo>) {

        // set 설정을 통해 이미 그려진 마커들은 다시 그리는 것을 막는다
        // 추가 구현 필요

        // 리뷰는 다 열려있고 자유는 가깝게 다가 가면 열린다.
        //

        for (i in markerLocationList) {
            val markerPosition =
                MapPoint.mapPointWithGeoCoord(i.latitude.toDouble(), i.longitude.toDouble())
            val marker = MapPOIItem()
            marker.apply {
                itemName = i.content
                mapPoint = markerPosition
                markerType = MapPOIItem.MarkerType.CustomImage

                if (i.campsiteName == ApplicationClass.preferences.userRecentCampsiteName) {
                    customImageResourceId = R.drawable.ic_community_campsite_open_note
                } else {
                    customImageResourceId = R.drawable.ic_community_campsite_close_note
                }
                userObject = i

                /*selectedMarkerType = MapPOIItem.MarkerType.CustomImage
                customSelectedImageResourceId = R.drawable.ic_community_campsite_marker*/

            }
            mapView.addPOIItem(marker)
        }
    }

    override fun onMapViewInitialized(p0: MapView?) {
        Log.d(TAG, "onMapViewInitialized: ")
    }

    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {
        Log.d(TAG, "onMapViewCenterPointMoved: ")
    }

    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {
        communityCampsiteViewModel.getCampsiteMessageBriefInfoByScope(
            mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
            mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude,
            ApplicationClass.preferences.userRecentCampsiteId.toString(),
            mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude,
            mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude
        )
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
        communityCampsiteViewModel.getCampsiteMessageBriefInfoByScope(
            mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
            mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude,
            ApplicationClass.preferences.userRecentCampsiteId.toString(),
            mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude,
            mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude
        )
    }


    // 마커 클릭 이벤트 리스너
    /*class MarkerEventListener(val context: Context) : MapView.POIItemEventListener {
        override fun onPOIItemSelected(mapView: MapView?, poiItem: MapPOIItem?) {
            // 마커 클릭 시
        }

        override fun onCalloutBalloonOfPOIItemTouched(mapView: MapView?, poiItem: MapPOIItem?) {
            // 말풍선 클릭 시 (Deprecated)
            // 이 함수도 작동하지만 그냥 아래 있는 함수에 작성하자
        }

        override fun onCalloutBalloonOfPOIItemTouched(
            mapView: MapView?,
            poiItem: MapPOIItem?,
            buttonType: MapPOIItem.CalloutBalloonButtonType?
        ) {
            // 말풍선 클릭 시
            *//*val builder = AlertDialog.Builder(context)
            val itemList = arrayOf("토스트", "마커 삭제", "취소")
            builder.setTitle("${poiItem?.itemName}")
            builder.setItems(itemList) { dialog, which ->
                when(which) {
                    0 -> Toast.makeText(context, "토스트", Toast.LENGTH_SHORT).show()  // 토스트
                    1 -> mapView?.removePOIItem(poiItem)    // 마커 삭제
                    2 -> dialog.dismiss()   // 대화상자 닫기
                }
            }
            builder.show()*//*
        }

        override fun onDraggablePOIItemMoved(
            mapView: MapView?,
            poiItem: MapPOIItem?,
            mapPoint: MapPoint?
        ) {
            // 마커의 속성 중 isDraggable = true 일 때 마커를 이동시켰을 경우
        }
    }*/

    override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
        val message: CampsiteMessageBriefInfo = p1!!.userObject as CampsiteMessageBriefInfo
        getFreeReviewDetail(message.messageId)
        //CommunityCampsiteFreeReviewDetailDialog(requireContext(), message.messageId).show()
    }

    override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {

    }

    override fun onCalloutBalloonOfPOIItemTouched(
        p0: MapView?,
        p1: MapPOIItem?,
        p2: MapPOIItem.CalloutBalloonButtonType?
    ) {

    }

    override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {

    }

    override fun getFreeReviewDetail(messageId: String) {
        communityCampsiteViewModel.getCampsiteMessageDetailInfo(messageId)
        communityCampsiteViewModel.campsiteMessageDetailInfo.observe(viewLifecycleOwner) { response ->
            response.let {
                CommunityCampsiteFreeReviewDetailDialog(requireContext(), it).show()
            }
        }
    }

    // 내 위치 권한 허용 함수들


}