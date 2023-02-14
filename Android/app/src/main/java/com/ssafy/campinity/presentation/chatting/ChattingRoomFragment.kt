package com.ssafy.campinity.presentation.chatting

import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentChattingRoomBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChattingRoomFragment :
    BaseFragment<FragmentChattingRoomBinding>(R.layout.fragment_chatting_room) {

    private val chatItemAdapter by lazy { ChatItemAdapter() }

    override fun initView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvChat.adapter = chatItemAdapter
    }
}