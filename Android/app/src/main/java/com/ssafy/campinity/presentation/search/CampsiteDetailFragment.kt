package com.ssafy.campinity.presentation.search

import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.getDeviceWidthDp
import com.ssafy.campinity.databinding.FragmentCampsiteDetailBinding
import com.ssafy.campinity.presentation.base.BaseFragment

class CampsiteDetailFragment() :
    BaseFragment<FragmentCampsiteDetailBinding>(R.layout.fragment_campsite_detail) {
    private lateinit var images: List<String>
    private var campsiteId: String = ""

    override fun initView() {
        val args: CampsiteDetailFragmentArgs by navArgs()
        campsiteId = args.campsiteId

        binding.vpCampsiteImage.apply {
            adapter = CampsiteDetailImageAdapter(images)
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }
        binding.ivIndicator.apply {
            setSliderHeight(5F)
            setSliderGap(0F)
            setupWithViewPager(binding.vpCampsiteImage)

            setSliderWidth(
                getDeviceWidthDp(requireContext())
                        / if (images.isEmpty()) 1 else images.size
            )
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
    }
}