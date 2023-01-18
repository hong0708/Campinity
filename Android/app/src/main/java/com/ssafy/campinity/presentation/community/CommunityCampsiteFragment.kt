package com.ssafy.campinity.presentation.community

import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentCommunityCampsiteBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityCampsiteFragment :
    BaseFragment<FragmentCommunityCampsiteBinding>(R.layout.fragment_community_campsite) {

    private var isFabOpen = false
    private val darkBackground = Paint()

    override fun initView() {
        initListener()
    }

    private fun initListener() {

        // SlidingUpPanel
        val slidePanel = binding.slCommunityFrame
        // 이벤트 리스너 추가
        slidePanel.addPanelSlideListener(PanelEventListener())

        // 패널 열고 닫기
        binding.ibSelectCampsiteCondition.setOnClickListener {
            val state = slidePanel.panelState
            // 닫힌 상태일 경우 열기
            if (state == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            }
        }

        darkBackground.color = Color.BLACK
        darkBackground.alpha = 100
        
        binding.apply {
            fabMain.setOnClickListener {
                if (isFabOpen) {
                    // 플로팅 액션 버튼 닫기 - 열려있는 플로팅 버튼 집어넣는 애니메이션 세팅
                    ObjectAnimator.ofFloat(fabHelp, "translationY", 0f).apply { start() }
                    ObjectAnimator.ofFloat(fabGetHelp, "translationY", 0f).apply { start() }
                    ObjectAnimator.ofFloat(fabReview, "translationY", 0f).apply { start() }
                    ObjectAnimator.ofFloat(fabFreeNote, "translationY", 0f).apply { start() }
                    clMapBackSite.setBackgroundColor(Color.TRANSPARENT)
                } else {
                    // 플로팅 액션 버튼 열기 - 닫혀있는 플로팅 버튼 꺼내는 애니메이션 세팅
                    ObjectAnimator.ofFloat(fabHelp, "translationY", -800f).apply { start() }
                    ObjectAnimator.ofFloat(fabGetHelp, "translationY", -600f).apply { start() }
                    ObjectAnimator.ofFloat(fabReview, "translationY", -400f).apply { start() }
                    ObjectAnimator.ofFloat(fabFreeNote, "translationY", -200f).apply { start() }
                    clMapBackSite.setBackgroundColor(darkBackground.color)
                }
                isFabOpen = !isFabOpen
            }
        }
    }

    // 이벤트 리스너
    inner class PanelEventListener : SlidingUpPanelLayout.PanelSlideListener {
        // 패널이 슬라이드 중일 때
        override fun onPanelSlide(panel: View?, slideOffset: Float) {

        }

        // 패널의 상태가 변했을 때
        override fun onPanelStateChanged(
            panel: View?,
            previousState: SlidingUpPanelLayout.PanelState?,
            newState: SlidingUpPanelLayout.PanelState?
        ) {

        }
    }
}