package com.ssafy.campinity.presentation.search

import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentCampsiteBriefBinding
import com.ssafy.campinity.presentation.base.BaseFragment

class CampsiteBriefFragment :
    BaseFragment<FragmentCampsiteBriefBinding>(R.layout.fragment_campsite_brief) {

    override fun initView() {
        initListener()
    }

    private fun initListener() {
        binding.btnPostbox.setOnClickListener {
            navigate(SearchMainFragmentDirections.actionSearchMainFragmentToSearchPostboxFragment())
        }
    }
}