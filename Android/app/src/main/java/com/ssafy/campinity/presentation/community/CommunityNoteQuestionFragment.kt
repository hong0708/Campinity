package com.ssafy.campinity.presentation.community

import android.util.Log
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentCommunityNoteQuestionBinding
import com.ssafy.campinity.presentation.base.BaseFragment

class CommunityNoteQuestionFragment :
    BaseFragment<FragmentCommunityNoteQuestionBinding>(R.layout.fragment_community_note_question) {

    private val impl = arrayListOf<String>("1", "2", "3")

    override fun initView() {
        initListener()
        Log.d("왜?", "initView: ")
        binding.rvCommunityQuestionNote.adapter = CommunityNoteListAdapter(impl)
    }

    private fun initListener() {

    }
}