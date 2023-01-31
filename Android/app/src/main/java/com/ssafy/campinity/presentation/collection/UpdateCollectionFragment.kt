package com.ssafy.campinity.presentation.collection

import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentUpdateCollectionBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateCollectionFragment : BaseFragment<FragmentUpdateCollectionBinding>(R.layout.fragment_update_collection) {
    override fun initView() {
        initListener()
    }

    private fun initListener() {
        binding.ivArrowLeft.setOnClickListener { popBackStack() }
    }
}