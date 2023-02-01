package com.ssafy.campinity.presentation.curation

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentCurationDetailBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurationDetailFragment :
    BaseFragment<FragmentCurationDetailBinding>(R.layout.fragment_curation_detail) {

    private val args by navArgs<CurationDetailFragmentArgs>()
    private val curationViewModel by activityViewModels<CurationViewModel>()

    override fun initView() {
        initListener()
        getData()
        initViewPager()
    }

    private fun initListener() {
        binding.ivArrowLeft.setOnClickListener { popBackStack() }
    }

    private fun getData() {
        curationViewModel.getCuration(args.curationId)
        curationViewModel.curationData.observe(viewLifecycleOwner) {
            binding.curationDetail = it

        }
    }

    private fun initViewPager() {
        curationViewModel.curationData.observe(viewLifecycleOwner) {
            binding.vpCurationDetail.apply {
                adapter = CurationDetailImageAdapter(it!!.imagePaths)
                orientation = ViewPager2.ORIENTATION_HORIZONTAL
            }
        }
    }
}