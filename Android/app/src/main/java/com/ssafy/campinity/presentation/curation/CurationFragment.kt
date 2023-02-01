package com.ssafy.campinity.presentation.curation

import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentCurationBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurationFragment : BaseFragment<FragmentCurationBinding>(R.layout.fragment_curation) {

    private val curationViewModel by activityViewModels<CurationViewModel>()

    override fun initView() {
        initListener()
        initTabLayout()
    }

    private fun initListener() {
        binding.tlCuration.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                curationViewModel.categoryState.value = tab!!.text.toString()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun initTabLayout() {
        val tabTitles = listOf("전체", "튜토리얼", "레시피", "장소 추천")
        binding.apply {
            vpCuration.adapter = CurationAdapter(this@CurationFragment)
            TabLayoutMediator(
                tlCuration, vpCuration
            ) { tab, position ->
                tab.text = tabTitles[position]
            }.attach()
        }
    }
}