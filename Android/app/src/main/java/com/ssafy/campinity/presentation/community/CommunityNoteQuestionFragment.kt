package com.ssafy.campinity.presentation.community

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentCommunityNoteQuestionBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityNoteQuestionFragment :
    BaseFragment<FragmentCommunityNoteQuestionBinding>(R.layout.fragment_community_note_question) {

    private val communityNoteViewModel by activityViewModels<CommunityNoteViewModel>()
    private val communityNoteListAdapter by lazy {
        CommunityNoteListAdapter(this::getPost)
    }

    override fun initView() {
        initListener()
        initNote()
    }

    private fun initListener() {

    }

    private fun initNote() {
        binding.rvCommunityQuestionNote.apply {
            adapter = communityNoteListAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
        communityNoteViewModel.noteQuestions.observe(viewLifecycleOwner) { response ->
            response.let { communityNoteListAdapter.setCuration(it) }
        }
        communityNoteViewModel.requestNoteQuestions("68c156cf-3db2-41dd-8e4e-2e3b44d15179")
    }

    private fun getPost(noteQuestionId: String) {

    }
}