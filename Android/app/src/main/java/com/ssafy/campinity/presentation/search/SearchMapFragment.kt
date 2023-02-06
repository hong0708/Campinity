package com.ssafy.campinity.presentation.search

import androidx.fragment.app.activityViewModels
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentSearchMapBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import net.daum.mf.map.api.MapView

class SearchMapFragment : BaseFragment<FragmentSearchMapBinding>(R.layout.fragment_search_map) {

    private lateinit var mapView: MapView
    private val searchViewModel by activityViewModels<SearchViewModel>()

    override fun initView() {}

    override fun onResume() {
        super.onResume()

        mapView = MapView(requireContext())

        binding.rlMapView.addView(mapView)

        observeIsSearchAgain()
    }

    override fun onPause() {
        super.onPause()
        binding.rlMapView.removeView(mapView)
    }

    private fun observeIsSearchAgain() {
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
    }
}