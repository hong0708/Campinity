package com.ssafy.campinity.presentation.community.campsite

import android.Manifest
import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.ssafy.campinity.ApplicationClass
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.BindingAdapters.setProfileImgString
import com.ssafy.campinity.databinding.FragmentCommunityCampsiteBinding
import com.ssafy.campinity.domain.entity.community.CampsiteBriefInfo
import com.ssafy.campinity.domain.entity.community.CampsiteMessageBriefInfo
import com.ssafy.campinity.domain.entity.community.RecentUserLocation
import com.ssafy.campinity.domain.entity.community.UserLocation
import com.ssafy.campinity.presentation.base.BaseFragment
import com.ssafy.campinity.presentation.community.CommunityActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import java.lang.Math.*
import kotlin.math.pow

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.Q)
class CommunityCampsiteFragment :
    BaseFragment<FragmentCommunityCampsiteBinding>(R.layout.fragment_community_campsite),
    MapView.MapViewEventListener,
    MapView.POIItemEventListener,
    CommunityCampsiteFreeReviewDetailDialogInterface {

    private lateinit var viewList: List<View>
    private lateinit var fabList: List<ConstraintLayout>
    private lateinit var mapView: MapView
    private lateinit var callback: OnBackPressedCallback
    private val moveValues: List<Float> = listOf(600f, 400f, 200f)
    private val communityCampsiteTitleListAdapter by lazy {
        CommunityCampsiteTitleListAdapter(this::getCampsiteTitle)
    }
    private val communityCampsiteViewModel by activityViewModels<CommunityCampsiteViewModel>()
    private var isFabOpen = false
    private var isTracking = false
    private var newUserLocation = RecentUserLocation(0.0, 0.0)

    override fun initView() {

        // 로티 설정
        binding.laMapOpen.apply {
            if (ApplicationClass.preferences.isUserInCommunity == false.toString()) {
                addAnimatorListener(object : AnimatorListener {
                    override fun onAnimationStart(p0: Animator?) {}

                    override fun onAnimationEnd(p0: Animator?) {
                        binding.laMapOpen.visibility = View.GONE
                        binding.clBackOpenMap.visibility = View.GONE
                        ApplicationClass.preferences.isUserInCommunity = true.toString()
                    }

                    override fun onAnimationCancel(p0: Animator?) {}

                    override fun onAnimationRepeat(p0: Animator?) {}
                })
                setAnimation(R.raw.community_map)
                speed = 1.5f
                visibility = View.VISIBLE
                playAnimation()
            } else {
                binding.laMapOpen.visibility = View.GONE
                binding.clBackOpenMap.visibility = View.GONE
            }
        }

        communityCampsiteViewModel.checkIsUserIn(ApplicationClass.preferences.isUserIn.toBoolean())

        initToggle()
        initObserver()
        initListener()
        initRecyclerView()
        setTextWatcher()
        setSubscribeState()
        communityCampsiteViewModel.getUserProfile()

        // 현재 위치 값 받아오기
        val messageReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {

                val test = intent.getSerializableExtra("test") as UserLocation
                newUserLocation.latitude = test.latitude
                newUserLocation.longitude = test.longitude

                communityCampsiteViewModel.checkIsUserIn(
                    DistanceManager.getDistance(
                        test.latitude,
                        test.longitude,
                        ApplicationClass.preferences.userRecentCampsiteLatitude!!.toDouble(),
                        ApplicationClass.preferences.userRecentCampsiteLongitude!!.toDouble()
                    ) < 1000
                )
            }
        }
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(messageReceiver, IntentFilter("intent_action"))
    }

    override fun onResume() {
        super.onResume()
        mapView = MapView(activity)
        mapView.setMapViewEventListener(this)
        mapView.setPOIItemEventListener(this)
        mapView.setZoomLevel(1, true)
        binding.clCommunityMap.addView(mapView)

        if (checkPermission()) {
            initMapView()
        }
    }

    override fun onPause() {
        super.onPause()
        binding.clCommunityMap.removeView(mapView)
        ApplicationClass.preferences.recentUserLat = newUserLocation.latitude.toString()
        ApplicationClass.preferences.recentUserLng = newUserLocation.longitude.toString()
        ApplicationClass.preferences.isUserIn = communityCampsiteViewModel.isUserIn.value.toString()
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

    // 맵뷰 이벤트 관련
    override fun onMapViewInitialized(p0: MapView?) {}

    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {}

    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {

        when (p0?.zoomLevel) {
            in -2..4 -> {
                communityCampsiteViewModel.getCampsiteMessageBriefInfoByScope(
                    mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
                    mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude,
                    ApplicationClass.preferences.userRecentCampsiteId.toString(),
                    mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude,
                    mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude
                )
            }
            else -> {
                mapView.removeAllPOIItems()
                p0?.removeAllPOIItems()
                if (ApplicationClass.preferences.userRecentCampsiteLatitude != null
                    && ApplicationClass.preferences.userRecentCampsiteLongitude != null
                ) {
                    drawPostBox(
                        CampsiteBriefInfo(
                            ApplicationClass.preferences.userRecentCampsiteId.toString(),
                            ApplicationClass.preferences.userRecentCampsiteName.toString(),
                            ApplicationClass.preferences.userRecentCampsiteAddress.toString(),
                            ApplicationClass.preferences.userRecentCampsiteLatitude.toString(),
                            ApplicationClass.preferences.userRecentCampsiteLongitude.toString()
                        )
                    )
                }
            }
        }
    }

    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {}

    override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {}

    override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {}

    override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {}

    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {}

    override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {
        when (p0?.zoomLevel) {
            in -2..4 -> {
                communityCampsiteViewModel.getCampsiteMessageBriefInfoByScope(
                    mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
                    mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude,
                    ApplicationClass.preferences.userRecentCampsiteId.toString(),
                    mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude,
                    mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude
                )
            }
            else -> {

            }
        }
    }

    // 마커 클릭 관련
    override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
        if (p1!!.tag != 1) {
            val campsiteMessageBriefInfo = p1.userObject as CampsiteMessageBriefInfo

            if (campsiteMessageBriefInfo.messageCategory == "리뷰") {
                getFreeReviewDetail(campsiteMessageBriefInfo.messageId)
            } else {
                if (getDistance(
                        campsiteMessageBriefInfo.latitude.toDouble(),
                        campsiteMessageBriefInfo.longitude.toDouble()
                    ) < 100
                ) {
                    CoroutineScope(Dispatchers.Main).launch {
                        binding.laNoteOpen.apply {
                            addAnimatorListener(object : AnimatorListener {
                                override fun onAnimationStart(p0: Animator?) {}

                                override fun onAnimationEnd(p0: Animator?) {
                                    binding.laNoteOpen.visibility = View.GONE
                                }

                                override fun onAnimationCancel(p0: Animator?) {}

                                override fun onAnimationRepeat(p0: Animator?) {}
                            })
                            setAnimation(R.raw.unlocked)
                            speed = 1.5f
                            visibility = View.VISIBLE
                            playAnimation()
                        }
                        delay(3000)
                        getFreeReviewDetail(campsiteMessageBriefInfo.messageId)
                    }

                } else {
                    //아직 멀어서 불가능
                    showToast("쪽지와 더 근접한 위치에서 열어보세요!")
                }
            }
        } else {
            if (mapView.zoomLevel > 6) {
                mapView.setZoomLevel(3, true)

                mapView.setMapCenterPoint(
                    MapPoint.mapPointWithGeoCoord(
                        p1.mapPoint.mapPointGeoCoord.latitude,
                        p1.mapPoint.mapPointGeoCoord.longitude
                    ), true
                )
            } else {
                navigate(
                    CommunityCampsiteFragmentDirections.actionCommunityCampsiteFragmentToCommunityNoteActivity()
                )
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {
    }

    override fun onCalloutBalloonOfPOIItemTouched(
        p0: MapView?,
        p1: MapPOIItem?,
        p2: MapPOIItem.CalloutBalloonButtonType?
    ) {
    }

    override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {}

    override fun getFreeReviewDetail(messageId: String) {
        communityCampsiteViewModel.getCampsiteMessageDetailInfo(messageId)
    }

    private fun initToggle() {
        val onService = requireActivity() as CommunityActivity
        when (ApplicationClass.preferences.isSubScribing) {
            true -> {
                binding.toggleCampsite.isOn = true
                onService.startLocationBackground()
            }
            false -> {
                binding.toggleCampsite.isOn = false
                onService.stopLocationBackground()
                ApplicationClass.preferences.isSubScribing = false
                communityCampsiteViewModel.checkIsUserIn(false)
            }
        }
    }

    private fun initMapView() {
        if (ApplicationClass.preferences.userRecentCampsiteName == null) {
            binding.tvCampsiteCondition.text = "미 지정"
            setMapPosition()
        } else {
            mapView.removeAllPOIItems()
            setMapPosition()
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

            viewList = listOf(tvFabGetHelp, tvFabReview, tvFabFreeNote, clMapBackSite)
            fabList = listOf(clFabGetHelp, clFabReview, clFabFreeNote)
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

                    mapView.currentLocationTrackingMode =
                        MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeadingWithoutMapMoving
                    if (isTracking) {
                        isTracking = false
                    }
                }
            }

            tvSearchCampsiteByName.setOnClickListener {
                communityCampsiteViewModel.getCampsiteBriefInfoByCampName()
            }

            fabGetHelp.setOnClickListener {
                if (communityCampsiteViewModel.isUserIn.value == true) {
                    navigate(
                        CommunityCampsiteFragmentDirections
                            .actionCommunityCampsiteFragmentToCommunityHelpNoteActivity(
                                "도움 주기",
                                ApplicationClass.preferences.userRecentCampsiteId.toString(),
                                newUserLocation.latitude.toString(),
                                newUserLocation.longitude.toString()
                            )
                    )
                } else {
                    showToast("캠핑장 구독 및 해당 캠핑장에 위치해야합니다.")
                }
            }

            fabReview.setOnClickListener {
                getUserLoc()
                navigate(
                    CommunityCampsiteFragmentDirections
                        .actionCommunityCampsiteFragmentToCommunityCampsiteDialogActivity(
                            "리뷰 쪽지",
                            ApplicationClass.preferences.userRecentCampsiteId.toString(),
                            newUserLocation.latitude.toString(),
                            newUserLocation.longitude.toString()
                        )
                )
            }

            fabFreeNote.setOnClickListener {
                getUserLoc()
                navigate(
                    CommunityCampsiteFragmentDirections
                        .actionCommunityCampsiteFragmentToCommunityCampsiteDialogActivity(
                            "자유 쪽지",
                            ApplicationClass.preferences.userRecentCampsiteId.toString(),
                            newUserLocation.latitude.toString(),
                            newUserLocation.longitude.toString()
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
                    for (i in 0..2) {
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
        // 구독 초기화
        ApplicationClass.preferences.isSubScribing = false
        communityCampsiteViewModel.subscribeCampSite(
            "", ApplicationClass.preferences.fcmToken.toString()
        )
        binding.toggleCampsite.isOn = false
        val onService = requireActivity() as CommunityActivity
        onService.stopLocationBackground()

        mapView.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeadingWithoutMapMoving
        isTracking = false

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

        communityCampsiteViewModel.isUserIn.observe(viewLifecycleOwner) { response ->
            if (binding.toggleCampsite.isOn) {
                if (response) {
                    showToast("현재 캠핑장 범위에 포함됩니다.")
                    ApplicationClass.preferences.isSubScribing = true
                    communityCampsiteViewModel.subscribeCampSite(
                        ApplicationClass.preferences.userRecentCampsiteId.toString(),
                        ApplicationClass.preferences.fcmToken.toString()
                    )
                    ApplicationClass.preferences.isUserCanAnswer = true.toString()
                } else {
                    showToast("현재 캠핑장 범위에서 벗어납니다.")
                    ApplicationClass.preferences.isSubScribing = false
                    communityCampsiteViewModel.subscribeCampSite(
                        "", ApplicationClass.preferences.fcmToken.toString()
                    )
                    ApplicationClass.preferences.isUserCanAnswer = false.toString()
                }
            }
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
            isShowCalloutBalloonOnTouch = false
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
                isShowCalloutBalloonOnTouch = false
                if (i.messageCategory == "리뷰") {
                    customImageResourceId = R.drawable.ic_review_note_marker
                } else {
                    customImageResourceId = R.drawable.ic_community_campsite_close_note3
                }
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
        if (userNowLocation != null) {
            val userLatitude = userNowLocation.latitude
            val userLongitude = userNowLocation.longitude

            newUserLocation.latitude = userLatitude
            newUserLocation.longitude = userLongitude

            val uNowPosition = MapPoint.mapPointWithGeoCoord(userLatitude, userLongitude)
            mapView.setMapCenterPoint(uNowPosition, true)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun checkPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }

        val permissions: Array<String> = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )

        ActivityCompat.requestPermissions(requireActivity(), permissions, 0)
        activity?.onBackPressed()
        return false
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

    @SuppressLint("MissingPermission")
    private fun getUserLoc() {
        val lm: LocationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val userNowLocation: Location? =
            lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        //위도 , 경도
        if (userNowLocation != null) {
            val userLatitude = userNowLocation.latitude
            val userLongitude = userNowLocation.longitude

            newUserLocation.latitude = userLatitude
            newUserLocation.longitude = userLongitude
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun setSubscribeState() {
        val onService = requireActivity() as CommunityActivity
        binding.apply {
            toggleCampsite.colorOff = resources.getColor(R.color.white_smoke)
            toggleCampsite.colorOn = resources.getColor(R.color.wild_willow)
            toggleCampsite.setOnToggledListener { _, isOn ->
                if (isOn) {
                    if (ApplicationClass.preferences.userRecentCampsiteId != null) {
                        onService.startLocationBackground()
                        Log.d("세림", "setSubscribeState: 켜져있음")
                    } else {
                        showToast("캠핑장 설정이 필요합니다.")
                    }
                } else {
                    Log.d("세림", "setSubscribeState: 꺼져있음")
                    onService.stopLocationBackground()
                    ApplicationClass.preferences.isSubScribing = false
                    communityCampsiteViewModel.subscribeCampSite(
                        "", ApplicationClass.preferences.fcmToken.toString()
                    )
                    ApplicationClass.preferences.isUserCanAnswer = false.toString()
                }
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

    // 현재 위치 받아오는 이너 클래스
    inner class GetUserLocation() : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            val action = p1!!.action
        }
    }

    object DistanceManager {
        private const val R = 6372.8 * 1000
        /**
         * 두 좌표의 거리를 계산한다.
         *
         * @param lat1 위도1
         * @param lon1 경도1
         * @param lat2 위도2
         * @param lon2 경도2
         * @return 두 좌표의 거리(m)
         */
        fun getDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Int {
            val dLat = Math.toRadians(lat2 - lat1)
            val dLon = Math.toRadians(lon2 - lon1)
            val a =
                sin(dLat / 2).pow(2.0) + sin(dLon / 2).pow(2.0) * cos(Math.toRadians(lat1)) * cos(
                    Math.toRadians(lat2)
                )
            val c = 2 * asin(sqrt(a))
            return (R * c).toInt()
        }
    }
}