package com.ssafy.campinity.presentation.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.ssafy.campinity.databinding.ActivitySearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                binding.rlBackground.visibility = View.VISIBLE
                binding.rlSearchAgain.visibility = View.GONE
                binding.rlShowMap.visibility = View.VISIBLE
            }
        }
    }
}