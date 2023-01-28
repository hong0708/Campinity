package com.ssafy.campinity.presentation.search

import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentSearchMapBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import net.daum.mf.map.api.MapView

class SearchMapFragment : BaseFragment<FragmentSearchMapBinding>(R.layout.fragment_search_map) {
    private lateinit var mapView: MapView

    override fun initView() {}

    override fun onResume() {
        super.onResume()

        if (!this::mapView.isInitialized)
            mapView = MapView(requireContext())

        binding.rlMapView.addView(mapView)
    }
}