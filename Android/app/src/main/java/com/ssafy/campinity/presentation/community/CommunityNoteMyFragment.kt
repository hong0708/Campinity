package com.ssafy.campinity.presentation.community

import android.util.Log
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentCommunityNoteMyBinding
import com.ssafy.campinity.presentation.base.BaseFragment

class CommunityNoteMyFragment :
    BaseFragment<FragmentCommunityNoteMyBinding>(R.layout.fragment_community_note_my) {

    private val impl = arrayListOf<String>("a", "b", "c")

    override fun initView() {
        initListener()
        Log.d("왜?", "initView: ")
        binding.rvCommunityMyNote.adapter = CommunityNoteListAdapter(impl)
    }

    private fun initListener() {

    }
}