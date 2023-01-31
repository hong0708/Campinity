package com.ssafy.campinity.presentation.community.note

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentCommunityNoteDetailBinding
import com.ssafy.campinity.presentation.base.BaseFragment

class CommunityNoteDetailFragment :
    BaseFragment<FragmentCommunityNoteDetailBinding>(R.layout.fragment_community_note_detail) {

    private val args by navArgs<CommunityNoteDetailFragmentArgs>()
    private val communityNoteViewModel by activityViewModels<CommunityNoteViewModel>()
    private val communityNoteQuestionAnswerAdapter by lazy {
        CommunityNoteQuestionAnswerAdapter()
    }

    override fun initView() {
        initNoteDetail()
        initListener()
    }

    private fun initListener(){
        binding.ivCloseNoteDetail.setOnClickListener {
            popBackStack()
        }
    }

    private fun initNoteDetail() {
        binding.rvNoteDetailAnswer.apply {
            adapter = communityNoteQuestionAnswerAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
        communityNoteViewModel.requestNoteQuestionDetail(
            /*args.questionId*/
        "ab1a3f40-0b99-4691-b2a1-b55449265498"
        )
        communityNoteViewModel.noteQuestionDetail.observe(viewLifecycleOwner) { response ->
            response?.let {
                binding.tvNoteQuestionContent.text = it.content
                communityNoteQuestionAnswerAdapter.setAnswer(it.answers)
            }
        }
    }
}