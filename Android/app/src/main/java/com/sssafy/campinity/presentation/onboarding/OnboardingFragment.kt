package com.sssafy.campinity.presentation.onboarding

import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentOnboardingBinding
import com.sssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFragment : BaseFragment<FragmentOnboardingBinding>(R.layout.fragment_onboarding) {
    override fun initView() {
        binding.apply {
            vpBanner.adapter = OnBoardingAdapter(this@OnboardingFragment)
            ciBanner.setViewPager(binding.vpBanner)
        }
    }
}