package com.ssafy.campinity.presentation.onboarding

import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentJoinBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JoinFragment : BaseFragment<FragmentJoinBinding>(R.layout.fragment_join) {
    override fun initView() {
        initListener()
    }

    private fun initListener() {
        binding.btnBack.setOnClickListener {
            // 다이얼로그 추가 필요
            popBackStack()
        }
    }
}