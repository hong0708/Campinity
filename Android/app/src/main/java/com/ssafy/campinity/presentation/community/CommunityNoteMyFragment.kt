package com.ssafy.campinity.presentation.community

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentCommunityNoteMyBinding
import com.ssafy.campinity.presentation.base.BaseFragment

class CommunityNoteMyFragment :
    BaseFragment<FragmentCommunityNoteMyBinding>(R.layout.fragment_community_note_my) {

    private val impl = arrayListOf<String>("a", "b", "c")

    override fun initView() {
        initListener()
    }

    override fun onResume() {
        super.onResume()
        binding.rvCommunityMyNote.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = CommunityNoteListAdapter(impl)
        }
    }

    private fun initListener() {

    }
}