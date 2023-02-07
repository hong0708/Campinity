package com.ssafy.campinity.presentation.search

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentSearchLetterDetailBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import com.ssafy.campinity.presentation.community.note.CommunityNoteQuestionAnswerAdapter
import com.ssafy.campinity.presentation.community.note.CommunityNoteViewModel

class SearchLetterDetailFragment :
    BaseFragment<FragmentSearchLetterDetailBinding>(R.layout.fragment_search_letter_detail) {

    private val args by navArgs<SearchLetterDetailFragmentArgs>()
    private val communityNoteViewModel by activityViewModels<CommunityNoteViewModel>()
    private val communityNoteQuestionAnswerAdapter by lazy {
        CommunityNoteQuestionAnswerAdapter()
    }

    override fun initView() {
        initListener()
        initLetterDetail()
    }

    private fun initListener() {
        binding.ivCloseLetterDetail.setOnClickListener {
            popBackStack()
        }
    }

    private fun initLetterDetail() {
        binding.rvLetterDetailAnswer.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = communityNoteQuestionAnswerAdapter
        }
        communityNoteViewModel.noteQuestionDetail.observe(viewLifecycleOwner) {
            it?.let {
                binding.tvLetterQuestionContent.text = it.content
                communityNoteQuestionAnswerAdapter.setAnswer(it.answers)
            }
        }
        communityNoteViewModel.getNoteQuestionDetail(args.questionId)
    }
}