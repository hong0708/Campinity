package com.ssafy.campinity.presentation.curation

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentCurationDetailBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

        binding.ibScrap.setOnClickListener {
            lifecycleScope.launch {
                val isScraped = curationViewModel.scrapCuration(args.curationId)
                if (isScraped == "true")
                    binding.ibScrap.setBackgroundResource(R.drawable.ic_bookmark_on)
                else if (isScraped == "false")
                    binding.ibScrap.setBackgroundResource(R.drawable.ic_bookmark_off)
            }
        }
    }

    private fun getData() {
        curationViewModel.getCuration(args.curationId)
        curationViewModel.curationData.observe(viewLifecycleOwner) {
            binding.curationDetail = it
            if (curationViewModel.curationData.value!!.isScraped)
                binding.ibScrap.setBackgroundResource(R.drawable.ic_bookmark_on)
            else
                binding.ibScrap.setBackgroundResource(R.drawable.ic_bookmark_off)
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