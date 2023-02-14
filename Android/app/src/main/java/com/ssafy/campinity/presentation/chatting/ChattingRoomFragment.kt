package com.ssafy.campinity.presentation.chatting

import android.content.Context
import androidx.activity.OnBackPressedCallback
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentChattingRoomBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChattingRoomFragment :
    BaseFragment<FragmentChattingRoomBinding>(R.layout.fragment_chatting_room) {

    private lateinit var callback: OnBackPressedCallback
    private val chatItemAdapter by lazy { ChatItemAdapter() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

    override fun initView() {
        initRecyclerView()
        initListener()
    }

    private fun initRecyclerView() {
        binding.rvChat.adapter = chatItemAdapter
    }

    private fun initListener() {
        binding.ivArrowLeft.setOnClickListener { popBackStack() }
    }
}