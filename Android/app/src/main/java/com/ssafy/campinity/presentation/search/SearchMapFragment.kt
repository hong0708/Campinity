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
import com.ssafy.campinity.presentation.base.BaseFragment
import kotlinx.coroutines.launch
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class SearchMapFragment : BaseFragment<FragmentSearchMapBinding>(R.layout.fragment_search_map),
    MapView.POIItemEventListener {

    private lateinit var mapView: MapView
    private val searchViewModel by activityViewModels<SearchViewModel>()

    override fun initView() {}

    override fun onResume() {
        super.onResume()

        mapView = MapView(requireContext())
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
            if (campsiteBriefInfoList != null && campsiteBriefInfoList.isNotEmpty()) {
                campsiteBriefInfoList.forEach {
                    searchViewModel.getCampsiteReviewNotes(
                        it.campsiteId,
                    )
                }

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
                markerType = MapPOIItem.MarkerType.CustomImage
                customImageResourceId = R.drawable.ic_review_note_marker
                isShowCalloutBalloonOnTouch = false
            }
            mapView.addPOIItem(marker)
        }
    }

    private fun navigationToSearchPostboxFragment() {
        navigate(SearchMainFragmentDirections.actionSearchMainFragmentToSearchPostboxFragment())
    }

    private fun navigationToCampsiteDetailFragment(async: Int) {
        navigate(
            SearchMainFragmentDirections.actionSearchMainFragmentToCampsiteDetailFragment(async)
        )
    }

    override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
        if (p1 != null) {
            val index = p1.itemName.split(" ")[1].toInt()
            p1.isMoveToCenterOnSelect = true

            if (p1.itemName.split(" ")[0] == "캠핑장") {
                if (searchViewModel.campsiteListData.value != null) {
                    CampsiteBriefDialog(
                        requireContext(),
                        searchViewModel.campsiteListData.value!![index],
                        this::navigationToSearchPostboxFragment,
                        this::onCampsiteClickListener
                    ).apply {
                        window?.setGravity(Gravity.BOTTOM)
                        window?.setDimAmount(0f)
                        show()
                    }
                }
            } else {
                if (searchViewModel.campsiteNoteList.value != null) {
                    lifecycleScope.launch {
                        val done =
                            searchViewModel.getCampsiteMessageDetailInfoAsync(searchViewModel.campsiteNoteList.value!![index].messageId)
                                .await()

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

    private fun onCampsiteClickListener(campsiteId: String) = lifecycleScope.launch {
        val async = searchViewModel.getCampsiteDetailAsync(campsiteId).await()
        navigationToCampsiteDetailFragment(async)
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
}