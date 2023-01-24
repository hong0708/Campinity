package com.ssafy.campinity.presentation.curation

import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentCurationBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurationFragment : BaseFragment<FragmentCurationBinding>(R.layout.fragment_curation) {
    override fun initView() {
        binding.vpCuration.adapter = CurationAdapter(this@CurationFragment)
    }
}