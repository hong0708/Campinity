package com.ssafy.campinity.presentation.chatting

import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentChattingListBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChattingListFragment :
    BaseFragment<FragmentChattingListBinding>(R.layout.fragment_chatting_list) {

    private val chatRoomAdapter by lazy { ChatRoomAdapter() }

    override fun initView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvChatRoom.adapter = chatRoomAdapter
    }
}