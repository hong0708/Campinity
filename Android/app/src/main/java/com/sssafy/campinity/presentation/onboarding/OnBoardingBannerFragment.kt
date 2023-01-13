package com.sssafy.campinity.presentation.onboarding

import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentOnboardingBannerBinding
import com.sssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingBannerFragment : BaseFragment<FragmentOnboardingBannerBinding>(R.layout.fragment_onboarding_banner) {

    private var bannerPosition = -1
    private val bannerContentList by lazy { resources.getStringArray(R.array.content_on_boarding_banner) }
    private val bannerSubContentList by lazy { resources.getStringArray(R.array.content_on_boarding_banner_sub) }

    override fun initView() {

        val bannerData = listOf(
            OnBoardingBanner(R.drawable.ic_onboarding_tent, bannerContentList[0], bannerSubContentList[0]),
            OnBoardingBanner(R.drawable.ic_onboarding_people, bannerContentList[1], bannerSubContentList[1]),
            OnBoardingBanner(R.drawable.ic_onboarding_camping, bannerContentList[2], bannerSubContentList[2]),
        )

        bannerPosition = arguments?.getInt("banner_position")!!
        with(bannerData[bannerPosition]) {
            binding.ivBanner.setImageResource(img)
            binding.tvBannerContent.text = title
            binding.tvBannerSubContent.text = content
        }
    }
}