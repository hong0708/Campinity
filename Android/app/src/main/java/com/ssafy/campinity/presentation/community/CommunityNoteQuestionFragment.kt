package com.ssafy.campinity.presentation.community

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentCommunityNoteQuestionBinding
import com.ssafy.campinity.presentation.base.BaseFragment

class CommunityNoteQuestionFragment :
    BaseFragment<FragmentCommunityNoteQuestionBinding>(R.layout.fragment_community_note_question) {

    private val impl = arrayListOf<String>("1", "2", "3")

    private val communityNoteViewModel by activityViewModels<CommunityNoteViewModel>()

    override fun initView() {
        initListener()
        observeCommunityNoteViewModel()
    }

    override fun onResume() {
        super.onResume()
        binding.rvCommunityQuestionNote.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = CommunityNoteListAdapter(impl)
        }
    }

    private fun initListener() {

    }

    private fun observeCommunityNoteViewModel() {
        communityNoteViewModel.noteMyQuestions.observe(viewLifecycleOwner) {

        }
    }
}