package com.ssafy.campinity.presentation.community.note

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentCommunityNoteMyBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityNoteMyFragment :
    BaseFragment<FragmentCommunityNoteMyBinding>(R.layout.fragment_community_note_my) {

    private val communityNoteViewModel by activityViewModels<CommunityNoteViewModel>()
    private val communityNoteListAdapter by lazy {
        CommunityNoteListAdapter(this::getPost)
    }

    override fun initView() {
        initListener()
    }

    override fun onResume() {
        super.onResume()
        initNote()
    }

    private fun initListener() {

    }

    private fun initNote() {
        binding.rvCommunityMyNote.apply {
            adapter = communityNoteListAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
        communityNoteViewModel.noteMyQuestions.observe(viewLifecycleOwner) { response ->
            response?.let {
                communityNoteListAdapter.setNote(it)
            }
        }
        communityNoteViewModel.requestNoteMyQuestions("68c156cf-3db2-41dd-8e4e-2e3b44d15179")
    }

    private fun getPost(questionId: String) {
        navigate(
            CommunityNoteFragmentDirections.actionCommunityNoteFragmentToCommunityNoteDetailFragment(
                questionId
            )
        )
    }
}