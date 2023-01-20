package com.ssafy.campinity.presentation.search

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentSearchBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    override fun initView() {
        initListener()
    }

    private fun initListener() {
        binding.slSearchFrame.addPanelSlideListener(PanelEventListener())

        binding.rlShowList.setOnClickListener {
            binding.slSearchFrame.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
        }

        binding.rlShowMap.setOnClickListener {
            binding.slSearchFrame.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        }
    }

    inner class PanelEventListener : SlidingUpPanelLayout.PanelSlideListener {
        override fun onPanelSlide(panel: View?, slideOffset: Float) {
            binding.viewInvisible.background = ColorDrawable(Color.TRANSPARENT)
            binding.rlShowMap.visibility = View.GONE
        }

        override fun onPanelStateChanged(
            panel: View?,
            previousState: SlidingUpPanelLayout.PanelState?,
            newState: SlidingUpPanelLayout.PanelState?
        ) {
            if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                binding.rlBackground.visibility = View.INVISIBLE
                binding.rlSearchAgain.visibility = View.VISIBLE
            } else if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                binding.viewInvisible.setBackgroundResource(R.color.white)
                binding.rlBackground.visibility = View.VISIBLE
                binding.rlSearchAgain.visibility = View.GONE
                binding.rlShowMap.visibility = View.VISIBLE
            }
        }
    }
}