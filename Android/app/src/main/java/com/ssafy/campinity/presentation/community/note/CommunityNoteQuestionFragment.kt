package com.ssafy.campinity.presentation.community.note

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.ApplicationClass
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
        initNote()
    }

    private fun initNote() {
        binding.rvCommunityQuestionNote.apply {
            adapter = communityNoteListAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
        communityNoteViewModel.noteQuestions.observe(viewLifecycleOwner) { response ->
            response.let { communityNoteListAdapter.setNote(it) }
        }
        communityNoteViewModel.getNoteQuestions(ApplicationClass.preferences.userRecentCampsiteId.toString())
    }

    private fun getPost(questionId: String) {
        navigate(
            CommunityNoteFragmentDirections.actionCommunityNoteFragmentToCommunityNoteDetailFragment(
                questionId
            )
        )
    }
}