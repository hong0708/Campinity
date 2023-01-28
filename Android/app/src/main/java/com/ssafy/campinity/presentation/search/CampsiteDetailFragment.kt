package com.ssafy.campinity.presentation.search

import android.util.Log
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.getDeviceWidthPx
import com.ssafy.campinity.common.util.px
import com.ssafy.campinity.databinding.FragmentCampsiteDetailBinding
import com.ssafy.campinity.domain.entity.search.FacilityAndLeisureItem
import com.ssafy.campinity.presentation.base.BaseFragment

class CampsiteDetailFragment() :
    BaseFragment<FragmentCampsiteDetailBinding>(R.layout.fragment_campsite_detail) {
    private var images: List<String> = listOf()
    private lateinit var contentTheme: Array<String>
    private lateinit var contentFacility: Array<String>
    private lateinit var contentAmenity: Array<String>
    private var facilityAndLeisure = listOf<FacilityAndLeisureItem>()
    private var campsiteId: String = ""

    override fun initView() {
        val args: CampsiteDetailFragmentArgs by navArgs()
        campsiteId = args.campsiteId
        Log.d("CampsiteDetailFragment", campsiteId)

        initStringArray()

        createDummy()

        initViewPager()

        initRecyclerView()
    }

    private fun initStringArray() {
        contentTheme = resources.getStringArray(R.array.content_campsite_theme)
        contentFacility = resources.getStringArray(R.array.content_campsite_facility)
        contentAmenity = resources.getStringArray(R.array.content_campsite_amenity)
    }

    private fun initViewPager() {
        binding.vpCampsiteImage.apply {
            adapter = CampsiteDetailImageAdapter(images)
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }

        binding.ivIndicator.apply {
            setSliderHeight(5.px(requireContext()).toFloat())
            setSliderGap(0F)
            setupWithViewPager(binding.vpCampsiteImage)

            setSliderWidth(
                getDeviceWidthPx(requireContext()).toFloat() / images.size
            )
        }
    }

    private fun initRecyclerView() {
        binding.rvTheme.apply {
            layoutManager =
                LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            adapter = CampsiteFacilityAndLeisureAdapter(facilityAndLeisure)
        }
        binding.rvFacility.apply {
            layoutManager =
                LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            adapter = CampsiteFacilityAndLeisureAdapter(facilityAndLeisure)
        }
        binding.rvAmenity.apply {
            layoutManager =
                LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            adapter = CampsiteFacilityAndLeisureAdapter(facilityAndLeisure)
        }
    }

    private fun createDummy() {
        images = listOf(
            "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg?crop=0.752xw:1.00xh;0.175xw,0&resize=1200:*",
            "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/golden-retriever-royalty-free-image-506756303-1560962726.jpg?crop=0.672xw:1.00xh;0.166xw,0&resize=640:*",
            "https://media-cldnry.s-nbcnews.com/image/upload/rockcms/2022-08/220805-border-collie-play-mn-1100-82d2f1.jpg",
            "https://cdn.britannica.com/16/234216-050-C66F8665/beagle-hound-dog.jpg",
            "https://www.scotsman.com/webimg/b25lY21zOmFkZjJiZWQxLThjMTctNDEwMC1iMTgxLTgzZjM0ZmRlNWYzOTpjYWQyZWYzOS1kMGY3LTQ0ZDctOWJhYS1kMGYxNjkwMDFhZDM=.jpg?width=1200&enable=upscale",
            "https://www.collinsdictionary.com/images/full/dog_230497594.jpg"
        )

        facilityAndLeisure = listOf(
            FacilityAndLeisureItem(
                R.drawable.ic_content_campsite_amenity_1,
                contentAmenity[0]
            ),
            FacilityAndLeisureItem(
                R.drawable.ic_content_campsite_amenity_2,
                contentAmenity[1]
            ),
            FacilityAndLeisureItem(
                R.drawable.ic_content_campsite_amenity_3,
                contentAmenity[2]
            ),
            FacilityAndLeisureItem(
                R.drawable.ic_content_campsite_amenity_4,
                contentAmenity[3]
            ),
            FacilityAndLeisureItem(
                R.drawable.ic_content_campsite_amenity_5,
                contentAmenity[4]
            ),
            FacilityAndLeisureItem(
                R.drawable.ic_content_campsite_amenity_6,
                contentAmenity[5]
            ),
        )
    }
}