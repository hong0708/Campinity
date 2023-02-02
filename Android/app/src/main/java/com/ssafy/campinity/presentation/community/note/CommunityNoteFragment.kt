package com.ssafy.campinity.presentation.community.note

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentCommunityNoteBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class CommunityNoteFragment :
    BaseFragment<FragmentCommunityNoteBinding>(R.layout.fragment_community_note),
    CommunityNoteDialogInterface {

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

            tvMakeQuestion.setOnClickListener {
                CommunityNoteQuestionDialog(
                    requireContext(),
                    this@CommunityNoteFragment
                ).show()
            }
            ivPostBoxBack.setOnClickListener {
                navigate(CommunityNoteFragmentDirections.actionCommunityNoteFragmentToCommunityCampsiteFragment())
            }
        }
    }

    override fun postNote(id: String, content: String) {
        CoroutineScope(Dispatchers.IO).launch {
            if (communityNoteViewModel.postNoteQuestion(id, content)) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "질문이 등록되었습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "질문 등록이 실패하였습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}