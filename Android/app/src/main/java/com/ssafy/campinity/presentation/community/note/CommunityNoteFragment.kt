package com.ssafy.campinity.presentation.community.note

import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentCommunityNoteBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityNoteFragment :
    BaseFragment<FragmentCommunityNoteBinding>(R.layout.fragment_community_note),
    CommunityNoteQuestionDialogInterface {

    private val communityNoteViewModel by activityViewModels<CommunityNoteViewModel>()

    override fun initView() {
        initListener()
    }

    private fun initListener() {
        binding.apply {

            tlNote.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {}

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })

            vpNote.adapter = CommunityNoteVPAdapter(this@CommunityNoteFragment)
            val tabTitles = listOf("질문함", "내 편지함")
            TabLayoutMediator(
                tlNote,
                vpNote
            ) { tab, position ->
                tab.text = tabTitles[position]
            }.attach()
        }
        binding.tvMakeQuestion.setOnClickListener {
            CommunityNoteQuestionDialog(
                requireContext(),
                this
            ).show()
        }
    }

    override fun postNoteQuestion(campsiteId: String, content: String) {
        communityNoteViewModel.requestNotePostQuestion(campsiteId, content)
    }
}