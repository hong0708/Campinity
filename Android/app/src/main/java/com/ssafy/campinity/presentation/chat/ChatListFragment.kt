package com.ssafy.campinity.presentation.chat

import androidx.fragment.app.activityViewModels
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentChattingListBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatListFragment :
    BaseFragment<FragmentChattingListBinding>(R.layout.fragment_chatting_list) {

    private val chatViewModel by activityViewModels<ChatViewModel>()
    private val chatRoomAdapter by lazy { ChatRoomAdapter(this::getChatRoom) }

    override fun initView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvChatRoom.adapter = chatRoomAdapter
        chatViewModel.chatRoomsData.observe(viewLifecycleOwner) { response ->
            response?.let { chatRoomAdapter.setChatRoom(it) }
        }
        chatViewModel.getRooms()
    }

    private fun getChatRoom(roomId: String) {
        navigate(
            ChatListFragmentDirections.actionChatListFragmentToChatRoomFragment(
                roomId
            )
        )
    }
}