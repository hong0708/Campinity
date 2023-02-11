package com.ssafy.campinity.presentation.community.campsite

import android.Manifest
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.ssafy.campinity.ApplicationClass
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.BindingAdapters.setProfileImgString
import com.ssafy.campinity.common.util.CustomDialogInterface
import com.ssafy.campinity.common.util.Permission
import com.ssafy.campinity.databinding.FragmentCommunityCampsiteBinding
import com.ssafy.campinity.domain.entity.community.CampsiteBriefInfo
import com.ssafy.campinity.domain.entity.community.CampsiteMessageBriefInfo
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

@AndroidEntryPoint
class CommunityCampsiteFragment :
    BaseFragment<FragmentCommunityCampsiteBinding>(R.layout.fragment_community_campsite),
    CustomDialogInterface,
    MapView.MapViewEventListener,
    MapView.POIItemEventListener,
    CommunityCampsiteFreeReviewDetailDialogInterface {

    private lateinit var viewList: List<View>
    private lateinit var fabList: List<ConstraintLayout>
    private lateinit var mapView: MapView
    private lateinit var callback: OnBackPressedCallback
    private val moveValues: List<Float> = listOf(800f, 600f, 400f, 200f)
    private val communityCampsiteTitleListAdapter by lazy {
        CommunityCampsiteTitleListAdapter(this::getCampsiteTitle)
    }
    private val communityCampsiteViewModel by activityViewModels<CommunityCampsiteViewModel>()
    private var isFabOpen = false
    private var isTracking = false

    override fun initView() {
        initToggle()
        initObserver()
        initListener()
        initRecyclerView()
        setTextWatcher()
        setSubscribeState()
        communityCampsiteViewModel.getUserProfile()
    }

    override fun onResume() {
        super.onResume()
        mapView = MapView(activity)
        mapView.setMapViewEventListener(this)
        mapView.setPOIItemEventListener(this)
        mapView.setZoomLevel(1, true)
        binding.clCommunityMap.addView(mapView)
        checkPermission()
    }

    override fun onPause() {
        super.onPause()
        binding.clCommunityMap.removeView(mapView)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.slCommunityFrame.panelState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    binding.slCommunityFrame.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                } else {
                    onDetach()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

    override fun onFinishButton() {
        onDestroyView()
    }

    // 맵뷰 이벤트 관련
    override fun onMapViewInitialized(p0: MapView?) {}

    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {}

    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {
        communityCampsiteViewModel.getCampsiteMessageBriefInfoByScope(
            mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
            mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude,
            ApplicationClass.preferences.userRecentCampsiteId.toString(),
            mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude,
            mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude
        )
    }

    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {}

    override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {}

    override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {}

    override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {}

    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {}

    override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {
        communityCampsiteViewModel.getCampsiteMessageBriefInfoByScope(
            mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
            mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude,
            ApplicationClass.preferences.userRecentCampsiteId.toString(),
            mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude,
            mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude
        )
    }

    // 마커 클릭 관련
    override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {}

    @Deprecated("Deprecated in Java")
    override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {}

    override fun onCalloutBalloonOfPOIItemTouched(
        p0: MapView?,
        p1: MapPOIItem?,
        p2: MapPOIItem.CalloutBalloonButtonType?
    ) {
        if (p1!!.tag == 1) {
            navigate(
                CommunityCampsiteFragmentDirections.actionCommunityCampsiteFragmentToCommunityNoteActivity()
            )
        } else {
            val campsiteMessageBriefInfo = p1.userObject as CampsiteMessageBriefInfo

            if (campsiteMessageBriefInfo.messageCategory == "리뷰") {
                getFreeReviewDetail(campsiteMessageBriefInfo.messageId)
            } else {
                if (getDistance(
                        campsiteMessageBriefInfo.latitude.toDouble(),
                        campsiteMessageBriefInfo.longitude.toDouble()
                    ) < 100
                ) {
                    // 충분히 가까워서 유효
                    getFreeReviewDetail(campsiteMessageBriefInfo.messageId)
                } else {
                    //아직 멀어서 불가능
                }
            }
        }
    }

    override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {}

    override fun getFreeReviewDetail(messageId: String) {
        communityCampsiteViewModel.getCampsiteMessageDetailInfo(messageId)
    }

    private fun initToggle() {
        when(ApplicationClass.preferences.isSubScribing) {
            true -> binding.toggleCampsite.isOn = true
            false -> binding.toggleCampsite.isOn = false
        }
    }

    private fun initMapView() {
        if (ApplicationClass.preferences.userRecentCampsiteName == null) {
            binding.tvCampsiteCondition.text = "미 지정"
            setMapPosition()
        } else {
            mapView.removeAllPOIItems()

            // 최근 검색 기준으로 초기화
            val recentCampsite =
                CampsiteBriefInfo(
                    ApplicationClass.preferences.userRecentCampsiteId.toString(),
                    ApplicationClass.preferences.userRecentCampsiteName.toString(),
                    ApplicationClass.preferences.userRecentCampsiteAddress.toString(),
                    ApplicationClass.preferences.userRecentCampsiteLatitude.toString(),
                    ApplicationClass.preferences.userRecentCampsiteLongitude.toString()
                )
            binding.tvCampsiteCondition.text = recentCampsite.campsiteName

            mapView.setMapCenterPoint(
                MapPoint.mapPointWithGeoCoord(
                    recentCampsite.latitude.toDouble(),
                    recentCampsite.longitude.toDouble()
                ),
                true
            )

            CoroutineScope(Dispatchers.Main).launch {
                val deffered: Deferred<Int> = async {
                    communityCampsiteViewModel.getCampsiteMessageBriefInfoByScope(
                        mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
                        mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude,
                        recentCampsite.campsiteId,
                        mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude,
                        mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude
                    )
                    1
                }
                deffered.await()
                drawPostBox(recentCampsite)
            }
        }

        mapView.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeadingWithoutMapMoving
    }

    private fun initRecyclerView() {
        binding.rvCampsiteTitleList.apply {
            adapter = communityCampsiteTitleListAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private fun initListener() {
        binding.apply {

            viewList =
                listOf(tvFabHelp, tvFabGetHelp, tvFabReview, tvFabFreeNote, clMapBackSite)
            fabList = listOf(clFabHelp, clFabGetHelp, clFabReview, clFabFreeNote)
            slCommunityFrame.addPanelSlideListener(PanelEventListener())

            ibBackToMain.setOnClickListener {
                activity?.onBackPressed()
            }

            ibUserLocation.setOnClickListener {
                if (isTracking) {
                    showToast("위치 추적을 멈춥니다.")
                    mapView.currentLocationTrackingMode =
                        MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeadingWithoutMapMoving
                } else {
                    showToast("사용자의 위치를 추적합니다.")
                    mapView.currentLocationTrackingMode =
                        MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading
                }
                isTracking = !isTracking
            }

            clSearchByUserLocation.setOnClickListener {
                //추적 시작
                showToast("사용자의 위치와 지도 기준으로 검색 합니다.")

                CoroutineScope(Dispatchers.Main).launch {
                    mapView.currentLocationTrackingMode =
                        MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading
                    if (isTracking) {
                        isTracking = false
                    }
                    // 현재 내위치 기준으로 맵 포인트 가운데로 옮기기
                    setMapPosition()
                    mapView.setZoomLevel(6, true)
                    mapView.mapCenterPoint.mapPointScreenLocation
                    delay(1000)

                    communityCampsiteViewModel.getCampsiteBriefInfoByUserLocation(
                        mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
                        mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude,
                        mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude,
                        mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude
                    )
                }
            }

            tvSearchCampsiteByName.setOnClickListener {
                communityCampsiteViewModel.getCampsiteBriefInfoByCampName()
            }

            // 도움 주기 받기 기능 추후 추가
            fabHelp.setOnClickListener {
                navigate(
                    CommunityCampsiteFragmentDirections
                        .actionCommunityCampsiteFragmentToCommunityHelpNoteActivity(
                            "도움 주기",
                            ApplicationClass.preferences.userRecentCampsiteId.toString()
                        )
                )
            }

            fabGetHelp.setOnClickListener {
                navigate(
                    CommunityCampsiteFragmentDirections
                        .actionCommunityCampsiteFragmentToCommunityHelpNoteActivity(
                            "도움 받기",
                            ApplicationClass.preferences.userRecentCampsiteId.toString()
                        )
                )
            }

            fabReview.setOnClickListener {
                navigate(
                    CommunityCampsiteFragmentDirections
                        .actionCommunityCampsiteFragmentToCommunityCampsiteDialogActivity(
                            "리뷰 쪽지",
                            ApplicationClass.preferences.userRecentCampsiteId.toString()
                        )
                )
            }

            fabFreeNote.setOnClickListener {
                navigate(
                    CommunityCampsiteFragmentDirections
                        .actionCommunityCampsiteFragmentToCommunityCampsiteDialogActivity(
                            "자유 쪽지",
                            ApplicationClass.preferences.userRecentCampsiteId.toString()
                        )
                )
            }

            clCampsiteCondition.setOnClickListener {
                // 닫힌 상태일 경우 열기
                if (slCommunityFrame.panelState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    slCommunityFrame.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
                }
            }

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
        campsiteBriefInfo: CampsiteBriefInfo
    ) {
        // 해당 캠핑장에 대한 아이디를 넘겨줘서 맵에 마커 그리기
        // 최근 검색 기록을 위한 캠핑장 저장
        binding.tvCampsiteCondition.text = campsiteBriefInfo.campsiteName
        ApplicationClass.preferences.userRecentCampsiteId = campsiteBriefInfo.campsiteId
        ApplicationClass.preferences.userRecentCampsiteName = campsiteBriefInfo.campsiteName
        ApplicationClass.preferences.userRecentCampsiteLongitude = campsiteBriefInfo.longitude
        ApplicationClass.preferences.userRecentCampsiteLatitude = campsiteBriefInfo.latitude
        ApplicationClass.preferences.userRecentCampsiteAddress = campsiteBriefInfo.address

        binding.slCommunityFrame.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED

        drawPostBox(campsiteBriefInfo)
        // 받아온 위경도 기준으로 캠핑장 그리기
        val userLatitude = campsiteBriefInfo.latitude.toDouble()
        val userLongitude = campsiteBriefInfo.longitude.toDouble()
        val userNowPosition = MapPoint.mapPointWithGeoCoord(userLatitude, userLongitude)
        mapView.setMapCenterPoint(userNowPosition, true)

        // 쪽지 리스트 업데이트
        communityCampsiteViewModel.getCampsiteMessageBriefInfoByScope(
            mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
            mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude,
            campsiteBriefInfo.campsiteId,
            mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude,
            mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude
        )
        mapView.setZoomLevel(3, true)
    }

    private fun setTextWatcher() {
        binding.apply {
            etInputCampsiteName.addTextChangedListener {
                communityCampsiteViewModel.content.value =
                    binding.etInputCampsiteName.text.toString()
            }
        }
    }

    private fun initObserver() {
        communityCampsiteViewModel.campsiteMessageDetailInfo.observe(viewLifecycleOwner) { response ->
            response.let {
                CommunityCampsiteFreeReviewDetailDialog(requireContext(), it).show()
            }
        }
        communityCampsiteViewModel.campsiteMessageBriefInfo.observe(viewLifecycleOwner) { response ->
            drawMarkers(response)
        }

        communityCampsiteViewModel.campsiteBriefInfo.observe(viewLifecycleOwner) { response ->
            response.let { communityCampsiteTitleListAdapter.setCampsiteBriefInfo(it) }
        }

        communityCampsiteViewModel.profileImgStr.observe(viewLifecycleOwner) { response ->
            binding.ibUserLocation.setProfileImgString(response)
        }
    }

    private fun drawPostBox(campsite: CampsiteBriefInfo) {
        val markerPosition =
            MapPoint.mapPointWithGeoCoord(
                campsite.latitude.toDouble(),
                campsite.longitude.toDouble()
            )
        val marker = MapPOIItem()
        marker.apply {
            itemName = campsite.campsiteName
            isShowDisclosureButtonOnCalloutBalloon = true
            mapPoint = markerPosition
            markerType = MapPOIItem.MarkerType.CustomImage
            customImageResourceId = R.drawable.ic_community_campsite_marker3
            //isCustomImageAutoscale = true
            userObject = campsite
            tag = 1
        }
        mapView.addPOIItem(marker)
    }

    // 마커를 그리는 함수
    private fun drawMarkers(markerLocationList: List<CampsiteMessageBriefInfo>) {
        // set 설정을 통해 이미 그려진 마커들은 다시 그리는 것을 막는다
        // 추가 구현 필요
        // 자유는 가깝게 다가 가면 열린다.
        for (i in markerLocationList) {
            val markerPosition =
                MapPoint.mapPointWithGeoCoord(i.latitude.toDouble(), i.longitude.toDouble())
            val marker = MapPOIItem()
            marker.apply {
                itemName = i.content
                mapPoint = markerPosition
                markerType = MapPOIItem.MarkerType.CustomImage
                customImageResourceId = if (i.messageCategory == "리뷰") {
                    R.drawable.ic_review_note_marker
                } else {
                    R.drawable.ic_community_campsite_close_note3
                    /*if (getDistance(i.latitude.toDouble(), i.longitude.toDouble()) < 5) {
                        R.drawable.ic_community_campsite_open_note3
                    } else {
                        R.drawable.ic_community_campsite_close_note3
                    }*/
                }
                //isCustomImageAutoscale = false
                userObject = i
                tag = 2
            }
            mapView.addPOIItem(marker)
        }
    }

    @SuppressLint("MissingPermission")
    private fun setMapPosition() {
        val lm: LocationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val userNowLocation: Location? =
            lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        //위도 , 경도
        if(userNowLocation != null){
            val userLatitude = userNowLocation.latitude
            val userLongitude = userNowLocation.longitude
            val uNowPosition = MapPoint.mapPointWithGeoCoord(userLatitude, userLongitude)
            mapView.setMapCenterPoint(uNowPosition, true)
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            initMapView()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                Permission.ACCESS_FINE_LOCATION
            )
            checkPermission()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getDistance(lat: Double, lng: Double): Float {

        val lm: LocationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val userNowLocation: Location? =
            lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

        val myLoc = Location(LocationManager.NETWORK_PROVIDER)
        val targetLoc = Location(LocationManager.NETWORK_PROVIDER)
        myLoc.latitude = userNowLocation?.latitude!!
        myLoc.longitude = userNowLocation.longitude
        targetLoc.latitude = lat
        targetLoc.longitude = lng

        return myLoc.distanceTo(targetLoc)
    }

    @SuppressLint("ResourceAsColor")
    private fun setSubscribeState() {
        binding.toggleCampsite.colorOff = resources.getColor(R.color.white_smoke)
        binding.toggleCampsite.colorOn = resources.getColor(R.color.wild_willow)
        binding.toggleCampsite.setOnToggledListener { _, isOn ->
            if (isOn) {
                ApplicationClass.preferences.isSubScribing = true
                communityCampsiteViewModel.subscribeCampSite(
                    ApplicationClass.preferences.userRecentCampsiteId.toString(),
                    ApplicationClass.preferences.fcmToken.toString()
                )
            } else {
                ApplicationClass.preferences.isSubScribing = false
                communityCampsiteViewModel.subscribeCampSite(
                    "", ApplicationClass.preferences.fcmToken.toString())
            }
        }
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
}