package com.ssafy.campinity.presentation.search

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.util.Log
import android.view.Gravity
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
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

    override fun initView() {}

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

        mapView.setZoomLevel(1, true)
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
                        mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude
                    )
                    this.setIsSearchAgain(false)
                }
        }

        searchViewModel.campsiteListData.observe(viewLifecycleOwner) { campsiteBriefInfoList ->

            Log.d("세림", "observe: 캠핑장 리스트 데이터 옵저빙")

            if (campsiteBriefInfoList != null && campsiteBriefInfoList.isNotEmpty()) {
                campsiteBriefInfoList.forEach {
                    searchViewModel.getCampsiteReviewNotes(
                        it.campsiteId,
                    )
                }

                /*if (mapView.zoomLevel <7){

                }*/
                drawCampsiteMarkers(campsiteBriefInfoList)
                val campsiteMainPoint = MapPoint.mapPointWithGeoCoord(
                    campsiteBriefInfoList[0].lat,
                    campsiteBriefInfoList[0].lng
                )
                mapView.setMapCenterPoint(campsiteMainPoint, true)
                mapView.setZoomLevel(3, true)
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
                MapPoint.mapPointWithGeoCoord(campsiteBriefInfo.lat, campsiteBriefInfo.lng)
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

        if (p1!!.tag != 3 || p1.tag != 4) {

        } else {
            if (p1 != null) {
                val index = p1.itemName.split(" ")[1].toInt()

                if (p1.itemName.split(" ")[0] == "캠핑장") {
                    if (searchViewModel.campsiteListData.value != null) {
                        val markerPosition =
                            MapPoint.mapPointWithGeoCoord(
                                searchViewModel.campsiteListData.value!![index].lat,
                                searchViewModel.campsiteListData.value!![index].lng,
                            )
                        mapView.setMapCenterPoint(markerPosition, true)

                        CampsiteBriefDialog(
                            requireContext(),
                            index,
                            searchViewModel.campsiteListData.value!![index],
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
    }

    override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {}

    suspend fun scrapCampsite(position: Int, campsiteId: String): String =
        searchViewModel.scrapCampsite(position, campsiteId)

    // 해당 지도 이벤트 모션 함수
    override fun onMapViewInitialized(p0: MapView?) {}

    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {}

    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {

        Log.d("세림", "onMapViewZoomLevelChanged: ${p0?.zoomLevel}")

        when (p0?.zoomLevel) {
            in -2..7 -> {
                mapView.removeAllPOIItems()
                val a = searchViewModel.campsiteListData.value
                if (a != null) {
                    drawCampsiteMarkers(a)
                }
                val b = searchViewModel.campsiteNoteList.value
                if (b != null) {
                    drawReviewNoteMarkers(b)
                }
                /*communityCampsiteViewModel.getCampsiteMessageBriefInfoByScope(
                    mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
                    mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude,
                    ApplicationClass.preferences.userRecentCampsiteId.toString(),
                    mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude,
                    mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude
                )*/
            }
            in 5..9 -> {
                mapView.removeAllPOIItems()
                p0?.removeAllPOIItems()
                val siGunGuList = searchViewModel.campsiteListSiGunGuData.value
                drawSiGunGu(siGunGuList!!)
            }
            else -> {
                mapView.removeAllPOIItems()
                p0?.removeAllPOIItems()
                val doList = searchViewModel.campsiteListDoData.value
                drawDoMarker(doList!!)
            }
        }
    }

    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {}

    override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {}

    override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {}

    override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {}

    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {}

    override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {
        /*when (p0?.zoomLevel) {
            in -2..4 -> {

            }
            else -> {
            }
        }*/
    }

    private fun drawDoMarker(doMarkerLocationList: List<ClusteringDo>) {
        doMarkerLocationList.forEachIndexed { index, clusteringDo ->
            val markerPosition =
                MapPoint.mapPointWithGeoCoord(clusteringDo.latitude, clusteringDo.longitude)
            val marker = MapPOIItem()
            marker.apply {
                itemName = "캠핑장 $index"
                mapPoint = markerPosition
                tag = 4
                markerType = MapPOIItem.MarkerType.CustomImage
                customImageResourceId = R.drawable.ic_free_note_fab
                isShowCalloutBalloonOnTouch = false
            }
            mapView.addPOIItem(marker)
        }
    }

    private fun drawSiGunGu(siGunGuMarkerLocationList: List<ClusteringSiGunGu>) {
        siGunGuMarkerLocationList.forEachIndexed { index, clusteringSiGunGu ->
            val markerPosition =
                MapPoint.mapPointWithGeoCoord(
                    clusteringSiGunGu.latitude,
                    clusteringSiGunGu.longitude
                )
            val marker = MapPOIItem()
            marker.apply {
                itemName = "캠핑장 $index"
                mapPoint = markerPosition
                tag = 3
                markerType = MapPOIItem.MarkerType.CustomImage
                customImageResourceId = R.drawable.ic_free_note_fab
                isShowCalloutBalloonOnTouch = false
            }
            mapView.addPOIItem(marker)
        }
    }
}