package com.ssafy.campinity.presentation.search

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.util.Log
import android.view.WindowManager
import androidx.fragment.app.activityViewModels
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentSearchMapBinding
import com.ssafy.campinity.domain.entity.search.CampsiteBriefInfo
import com.ssafy.campinity.presentation.base.BaseFragment
import com.ssafy.campinity.presentation.community.campsite.CommunityCampsiteViewModel
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class SearchMapFragment : BaseFragment<FragmentSearchMapBinding>(R.layout.fragment_search_map),
    MapView.POIItemEventListener {

    private lateinit var mapView: MapView
    private val searchViewModel by activityViewModels<SearchViewModel>()
    private val communityCampsiteViewModel by activityViewModels<CommunityCampsiteViewModel>()

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
        val lm: LocationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val userNowLocation: Location? = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        val uLatitude = userNowLocation?.latitude
        val uLongitude = userNowLocation?.longitude
        val uNowPosition = MapPoint.mapPointWithGeoCoord(uLatitude!!, uLongitude!!)

        mapView.setMapCenterPoint(uNowPosition, true)
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

        searchViewModel.campsiteListData.observe(viewLifecycleOwner) {
            if (it != null && it.isNotEmpty()) {
                drawMarkers(it)
                val campsiteMainPoint = MapPoint.mapPointWithGeoCoord(it[0].lat, it[0].lng)
                mapView.setMapCenterPoint(campsiteMainPoint, true)
                mapView.setZoomLevel(3, true)
            }
        }
    }

    private fun drawMarkers(markerLocationList: List<CampsiteBriefInfo>) {
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

    override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
        if (p1 != null) {
            val index = p1.itemName.split(" ")[1].toInt()
            p1.isMoveToCenterOnSelect = true

            Log.d(
                "onPOIItemSelected",
                "onPOIItemSelected: ${searchViewModel.campsiteListData.value!![index]}"
            )

            if (searchViewModel.campsiteListData.value != null) {
                val dialog = CampsiteBriefDialog(
                    requireContext(),
                    searchViewModel.campsiteListData.value!![index],
                    this::actionSearchMainFragmentToSearchPostboxFragment
                )
                val params = dialog.window!!.attributes
                params.width = WindowManager.LayoutParams.MATCH_PARENT
                params.height = WindowManager.LayoutParams.MATCH_PARENT
                dialog.window!!.attributes = params
                dialog.show()
            }
        }
    }

    override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {}

    override fun onCalloutBalloonOfPOIItemTouched(
        p0: MapView?,
        p1: MapPOIItem?,
        p2: MapPOIItem.CalloutBalloonButtonType?
    ) {
    }

    override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {}

    private fun actionSearchMainFragmentToSearchPostboxFragment() {
        navigate(SearchMainFragmentDirections.actionSearchMainFragmentToSearchPostboxFragment())
    }
}