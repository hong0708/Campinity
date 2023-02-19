package com.ssafy.campinity.presentation.search

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.ssafy.campinity.ApplicationClass
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentSearchMapBinding
import com.ssafy.campinity.domain.entity.search.CampsiteBriefInfo
import com.ssafy.campinity.domain.entity.search.CampsiteNoteBriefInfo
import com.ssafy.campinity.domain.entity.search.ClusteringDo
import com.ssafy.campinity.domain.entity.search.ClusteringSiGunGu
import com.ssafy.campinity.presentation.base.BaseFragment
import kotlinx.coroutines.launch
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class SearchMapFragment : BaseFragment<FragmentSearchMapBinding>(R.layout.fragment_search_map),
    MapView.POIItemEventListener,
    MapView.MapViewEventListener {

    private lateinit var mapView: MapView
    private val searchViewModel by activityViewModels<SearchViewModel>()

    override fun initView() {
        binding.laMapOpen.apply {
            if (ApplicationClass.preferences.isUserInSearch == false.toString()) {
                addAnimatorListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(p0: Animator?) {}

                    override fun onAnimationEnd(p0: Animator?) {
                        binding.laMapOpen.visibility = View.GONE
                        binding.clBackOpenMap.visibility = View.GONE
                        ApplicationClass.preferences.isUserInSearch = true.toString()
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
    }

    override fun onResume() {
        super.onResume()

        mapView = MapView(requireContext())
        mapView.setMapViewEventListener(this)
        binding.rlMapView.addView(mapView)

        initFragment()
        observe()
    }

    override fun onPause() {
        super.onPause()
        binding.rlMapView.removeView(mapView)
    }

    @SuppressLint("MissingPermission")
    private fun initFragment() {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_DENIED
            && ContextCompat.checkSelfPermission(
                requireActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_DENIED
        ) {
            Log.d("SearchMapFragment", "PermissionCheck is denied")
        } else {
            val lm: LocationManager =
                requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager

            lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).apply {
                if (this != null) {
                    val uLatitude = this.latitude
                    val uLongitude = this.longitude
                    val uNowPosition = MapPoint.mapPointWithGeoCoord(uLatitude, uLongitude)

                    mapView.setMapCenterPoint(uNowPosition, true)
                }
            }
        }

        mapView.setZoomLevel(6, true)
        mapView.setPOIItemEventListener(this)
    }

    private fun observe() {
        searchViewModel.isSearchAgain.observe(viewLifecycleOwner) {
            if (it)
                searchViewModel.apply {
                    getCampsitesByScope(
                        mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
                        mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude,
                        mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude,
                        mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude,
                        1
                    )
                    this.setIsSearchAgain(false)
                }
        }

        searchViewModel.campsiteListData.observe(viewLifecycleOwner) { campsiteBriefInfoList ->
            if (campsiteBriefInfoList?.data != null) {
                if (campsiteBriefInfoList.data.isNotEmpty()) {
                    campsiteBriefInfoList.data.forEach {
                        searchViewModel.getCampsiteReviewNotes(
                            it.campsiteId,
                        )
                    }

                    drawCampsiteMarkers(campsiteBriefInfoList.data)
                    val campsiteMainPoint = MapPoint.mapPointWithGeoCoord(
                        campsiteBriefInfoList.data[0].latitude,
                        campsiteBriefInfoList.data[0].longitude
                    )
                    mapView.setMapCenterPoint(campsiteMainPoint, true)
                    mapView.setZoomLevel(3, true)
                } else {
                    mapView.removeAllPOIItems()
                    showToast("검색 결과가 없습니다.")
                }
            }
        }

        searchViewModel.campsiteNoteList.observe(viewLifecycleOwner) {
            if (it != null && it.isNotEmpty())
                drawReviewNoteMarkers(it)
        }
    }

    private fun drawCampsiteMarkers(markerLocationList: List<CampsiteBriefInfo>) {
        markerLocationList.forEachIndexed { index, campsiteBriefInfo ->
            val markerPosition =
                MapPoint.mapPointWithGeoCoord(
                    campsiteBriefInfo.latitude,
                    campsiteBriefInfo.longitude
                )
            val marker = MapPOIItem()
            marker.apply {
                itemName = "캠핑장 $index"
                mapPoint = markerPosition
                tag = 1
                markerType = MapPOIItem.MarkerType.CustomImage
                customImageResourceId = R.drawable.ic_search_campsite_marker
                isShowCalloutBalloonOnTouch = false
            }
            mapView.addPOIItem(marker)
        }
    }

    private fun drawReviewNoteMarkers(markerLocationList: List<CampsiteNoteBriefInfo>) {
        markerLocationList.forEachIndexed { index, campsiteNoteBriefInfo ->
            val markerPosition =
                MapPoint.mapPointWithGeoCoord(
                    campsiteNoteBriefInfo.latitude,
                    campsiteNoteBriefInfo.longitude
                )
            val marker = MapPOIItem()
            marker.apply {
                itemName = "쪽지 $index"
                mapPoint = markerPosition
                tag = 2
                markerType = MapPOIItem.MarkerType.CustomImage
                customImageResourceId = R.drawable.ic_review_note_marker
                isShowCalloutBalloonOnTouch = false
            }
            mapView.addPOIItem(marker)
        }
    }

    private fun navigationToSearchPostboxFragment(campsiteId: String) {
        navigate(
            SearchMainFragmentDirections.actionSearchMainFragmentToSearchPostboxFragment(
                campsiteId
            )
        )
    }

    private fun navigationToCampsiteDetailFragment(async: Int, position: Int) {
        navigate(
            SearchMainFragmentDirections.actionSearchMainFragmentToCampsiteDetailFragment(
                async,
                position
            )
        )
    }

    override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {

        if (!(p1!!.tag == 3 || p1.tag == 4)) {
            val index = p1.itemName.split(" ")[1].toInt()

            if (p1.itemName.split(" ")[0] == "캠핑장") {
                if (searchViewModel.campsiteListData.value != null) {
                    val markerPosition =
                        MapPoint.mapPointWithGeoCoord(
                            searchViewModel.campsiteListData.value!!.data[index].latitude,
                            searchViewModel.campsiteListData.value!!.data[index].longitude,
                        )
                    mapView.setMapCenterPoint(markerPosition, true)

                    CampsiteBriefDialog(
                        requireContext(),
                        index,
                        searchViewModel.campsiteListData.value!!.data[index],
                        this::navigationToSearchPostboxFragment,
                        this::onCampsiteClickListener,
                        this::scrapCampsite
                    ).apply {
                        window?.setGravity(Gravity.BOTTOM)
                        window?.setDimAmount(0f)
                        show()
                    }
                }
            } else {
                if (searchViewModel.campsiteNoteList.value != null) {
                    lifecycleScope.launch {
                        val done = searchViewModel.getCampsiteMessageDetailInfo(
                            searchViewModel.campsiteNoteList.value!![index].messageId
                        )

                        if (done)
                            SearchReviewNoteDetailDialog(
                                requireContext(),
                                searchViewModel.campsiteMessageDetailInfo.value!!
                            ).show()
                    }
                }
            }
        }
    }

    private fun onCampsiteClickListener(position: Int, campsiteId: String) = lifecycleScope.launch {
        val async = searchViewModel.getCampsiteDetailAsync(campsiteId)
        navigationToCampsiteDetailFragment(async, position)
    }

    @Deprecated("Deprecated in Java")
    override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {
    }

    override fun onCalloutBalloonOfPOIItemTouched(
        p0: MapView?,
        p1: MapPOIItem?,
        p2: MapPOIItem.CalloutBalloonButtonType?
    ) {
        if (p1!!.tag == 4) {
            mapView.removeAllPOIItems()
            mapView.setZoomLevel(8, true)
            val item = p1.userObject as ClusteringDo
            val markerPosition =
                MapPoint.mapPointWithGeoCoord(item.latitude, item.longitude)
            mapView.setMapCenterPoint(markerPosition, true)
        } else if (p1.tag == 3) {
            mapView.removeAllPOIItems()
            mapView.setZoomLevel(5, true)
            val item = p1.userObject as ClusteringSiGunGu
            val markerPosition =
                MapPoint.mapPointWithGeoCoord(item.latitude, item.longitude)
            mapView.setMapCenterPoint(markerPosition, true)
        }
    }

    override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {}

    suspend fun scrapCampsite(position: Int, campsiteId: String): String =
        searchViewModel.scrapCampsite(position, campsiteId)

    // 해당 지도 이벤트 모션 함수
    override fun onMapViewInitialized(p0: MapView?) {}

    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {}

    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {

        when (p0?.zoomLevel) {
            in -2..6 -> {
                mapView.removeAllPOIItems()
                val a = searchViewModel.campsiteListData.value
                if (a != null) {
                    drawCampsiteMarkers(a.data)
                }
                val b = searchViewModel.campsiteNoteList.value
                if (b != null) {
                    drawReviewNoteMarkers(b)
                }
            }
            in 7..9 -> {
                mapView.removeAllPOIItems()
                p0?.removeAllPOIItems()
                val siGunGuList = searchViewModel.campsiteListSiGunGuData.value
                if (siGunGuList != null) {
                    drawSiGunGu(siGunGuList)
                }
            }
            else -> {
                mapView.removeAllPOIItems()
                p0?.removeAllPOIItems()
                val doList = searchViewModel.campsiteListDoData.value
                if (doList != null) {
                    drawDoMarker(doList)
                }
            }
        }
    }

    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {}

    override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {}

    override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {}

    override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {}

    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {}

    override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {}

    private fun drawDoMarker(doMarkerLocationList: List<ClusteringDo>) {

        doMarkerLocationList.forEachIndexed { index, doMarkerLocation ->
            val markerPosition =
                MapPoint.mapPointWithGeoCoord(
                    doMarkerLocation.latitude,
                    doMarkerLocation.longitude
                )
            val marker = MapPOIItem()
            marker.apply {
                itemName = doMarkerLocation.cnt.toString()
                mapPoint = markerPosition
                isShowDisclosureButtonOnCalloutBalloon = true
                markerType = MapPOIItem.MarkerType.CustomImage
                customImageResourceId = R.drawable.ic_do_80//_campsites
                userObject = doMarkerLocation
                tag = 4
            }
            mapView.addPOIItem(marker)
        }

        /*for (i in doMarkerLocationList) {
            val markerPosition =
                MapPoint.mapPointWithGeoCoord(i.latitude, i.longitude)
            val marker = MapPOIItem()
            marker.apply {
                itemName = i.cnt.toString()
                mapPoint = markerPosition
                markerType = MapPOIItem.MarkerType.CustomImage
                R.drawable.ic_free_note_fab
                isShowCalloutBalloonOnTouch = false
                userObject = i
                tag = 4
            }
            mapView.addPOIItem(marker)
        }*/
    }

    private fun drawSiGunGu(siGunGuMarkerLocationList: List<ClusteringSiGunGu>) {

        siGunGuMarkerLocationList.forEachIndexed { index, siGunGuMarkerLocation ->
            val markerPosition =
                MapPoint.mapPointWithGeoCoord(
                    siGunGuMarkerLocation.latitude,
                    siGunGuMarkerLocation.longitude
                )
            val marker = MapPOIItem()
            marker.apply {
                itemName = siGunGuMarkerLocation.cnt.toString()
                mapPoint = markerPosition
                isShowDisclosureButtonOnCalloutBalloon = true
                markerType = MapPOIItem.MarkerType.CustomImage
                customImageResourceId = R.drawable.ic_si_80_green/*_campsite_zone*/
                userObject = siGunGuMarkerLocation
                tag = 3
            }
            mapView.addPOIItem(marker)
        }

        /*for (i in siGunGuMarkerLocationList) {
            val markerPosition =
                MapPoint.mapPointWithGeoCoord(i.latitude.toDouble(), i.longitude.toDouble())
            val marker = MapPOIItem()
            marker.apply {
                itemName = i.cnt.toString()
                mapPoint = markerPosition
                markerType = MapPOIItem.MarkerType.CustomImage
                R.drawable.ic_free_note_fab
                isShowCalloutBalloonOnTouch = false
                userObject = i
                tag = 3
            }
            mapView.addPOIItem(marker)
        }*/
    }
}