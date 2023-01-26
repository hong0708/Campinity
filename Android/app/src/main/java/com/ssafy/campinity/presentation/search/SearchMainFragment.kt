package com.ssafy.campinity.presentation.search

import android.util.Log
import android.view.View
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentSearchMainBinding
import com.ssafy.campinity.presentation.base.BaseFragment

class SearchMainFragment : BaseFragment<FragmentSearchMainBinding>(R.layout.fragment_search_main) {
    override fun initView() {
        Log.e("SearchMainFragment", "create fragment")
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
            binding.rlBackground.visibility = View.VISIBLE
            binding.rlSearchAgain.visibility = View.GONE
        }

        override fun onPanelStateChanged(
            panel: View?,
            previousState: SlidingUpPanelLayout.PanelState?,
            newState: SlidingUpPanelLayout.PanelState?
        ) {
            if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                binding.rlSearchAgain.visibility = View.VISIBLE
                binding.rlBackground.visibility = View.INVISIBLE
            }
        }
    }
}