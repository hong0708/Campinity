package com.ssafy.campinity.presentation.search

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.LinearItemDecoration
import com.ssafy.campinity.common.util.getDeviceWidthPx
import com.ssafy.campinity.common.util.px
import com.ssafy.campinity.databinding.FragmentCampsiteDetailBinding
import com.ssafy.campinity.domain.entity.search.FacilityAndLeisureItem
import com.ssafy.campinity.presentation.base.BaseFragment

class CampsiteDetailFragment :
    BaseFragment<FragmentCampsiteDetailBinding>(R.layout.fragment_campsite_detail) {

    private lateinit var contentTheme: Array<String>
    private lateinit var contentFacility: Array<String>
    private lateinit var contentAmenity: Array<String>
    private var facilityAndLeisure = listOf<FacilityAndLeisureItem>()
    private val searchViewModel by activityViewModels<SearchViewModel>()

    override fun initView() {
        initStringArray()
        initViewPager()
        initRecyclerView()
        initListener()
    }

    private fun initStringArray() {
        contentTheme = resources.getStringArray(R.array.content_campsite_theme)
        contentFacility = resources.getStringArray(R.array.content_campsite_facility)
        contentAmenity = resources.getStringArray(R.array.content_campsite_amenity)
    }

    private fun initViewPager() {
        binding.vpCampsiteImage.apply {
            adapter =
                CampsiteDetailImageAdapter(searchViewModel.campsiteDate.value?.images ?: listOf())
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }

        binding.ivIndicator.apply {
            val count =
                if (searchViewModel.campsiteDate.value?.images != null) searchViewModel.campsiteDate.value?.images!!.size else 1
            setSliderHeight(5.px(requireContext()).toFloat())
            setSliderGap(0F)
            setupWithViewPager(binding.vpCampsiteImage)
            setSliderWidth(getDeviceWidthPx(requireContext()).toFloat() / count)
        }
    }

    private fun initRecyclerView() {
        binding.rvCampsiteFacilityAndLeisure.apply {
            layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false
            )
            adapter = CampsiteFacilityAndLeisureAdapter(facilityAndLeisure)

            addItemDecoration(LinearItemDecoration(context, RecyclerView.HORIZONTAL, 20))
        }

        binding.rvCampsiteReview.apply {
            layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false
            )
            adapter = CampsiteReviewAdapter(listOf())

            addItemDecoration(LinearItemDecoration(context, RecyclerView.VERTICAL, 20))
        }
    }

    private fun initListener() {
        binding.btnPostbox.setOnClickListener {
            navigate(
                CampsiteDetailFragmentDirections.actionCampsiteDetailFragmentToSearchPostboxFragment()
            )
        }
    }
}