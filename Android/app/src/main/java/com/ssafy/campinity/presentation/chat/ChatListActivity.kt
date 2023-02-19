package com.ssafy.campinity.presentation.chat

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.databinding.ActivityChatListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatListBinding
    private val chatViewModel by viewModels<ChatViewModel>()
    private val chatRoomAdapter by lazy { ChatRoomAdapter(this::getChatRoom) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        getData()
    }

    private fun initRecyclerView() {
        binding.rvChatRoom.adapter = chatRoomAdapter
        binding.rvChatRoom.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

    }

    private fun getData(){
        chatViewModel.chatRoomsData.observe(this) { response ->
            response?.let { chatRoomAdapter.setChatRoom(it) }
        }
        chatViewModel.getRooms()
    }

    private fun getChatRoom(roomId: String, subject: String) {
        val intent = Intent(this, ChatRoomActivity::class.java)
        intent.putExtra("roomId", roomId)
        intent.putExtra("subject", subject)
        startActivity(intent)
    }
}