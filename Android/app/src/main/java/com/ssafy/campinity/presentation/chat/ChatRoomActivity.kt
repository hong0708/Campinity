package com.ssafy.campinity.presentation.chat

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.ssafy.campinity.ApplicationClass
import com.ssafy.campinity.databinding.ActivityChatRoomBinding
import com.ssafy.campinity.domain.entity.chat.ChatItem
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent

@AndroidEntryPoint
class ChatRoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatRoomBinding
    private lateinit var chatItemAdapter: ChatItemAdapter
    private lateinit var roomId: String
    private lateinit var subject: String
    private val chatList: MutableList<ChatItem> = mutableListOf()
    private val url = "ws://i8d101.p.ssafy.io:8003/chat/websocket"
    private val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)
    private var connected = false

    @SuppressLint("CheckResult")
    override fun onResume() {
        super.onResume()
        if(!connected){
            connectStomp()
        }
        Log.d("getChatRoom", "onResume")
    }

    override fun onDestroy() {
        super.onDestroy()
        if(connected){
            connected = false
            stompClient.disconnect()
        }
    }

    override fun onStop() {
        super.onStop()
        if(connected){
            connected = false
            stompClient.disconnect()
            finish()
        }
    }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecyclerView()
        initListener()
        connectStomp()
        roomId = intent.getStringExtra("roomId").toString()
        subject = intent.getStringExtra("subject").toString()

        binding.tvChatSubject.text = subject


        binding.btnChatSend.setOnClickListener {
            val chatJson = JSONObject()
            chatJson.put("roomId", roomId)

            chatJson.put("sender", ApplicationClass.preferences.nickname.toString())
            chatJson.put("message", binding.etChat.text.toString())
            stompClient.send("/pub/chat/${roomId}", chatJson.toString()).subscribe()

            binding.etChat.setText("")
        }

        stompClient.lifecycle().subscribe { lifecycleEvent ->
            when (lifecycleEvent.type) {
                LifecycleEvent.Type.OPENED -> {
                    Log.i("OPEND", "!!")
                }
                LifecycleEvent.Type.CLOSED -> {
                    Log.i("CLOSED", "!!")
                    connected = false
                }
                LifecycleEvent.Type.ERROR -> {
                    Log.i("ERROR", "!!")
                    Log.e("CONNECT ERROR", lifecycleEvent.exception.toString())
                    connected = false
                }
                else -> {
                    Log.i("ELSE", lifecycleEvent.message)
                }
            }
        }
    }

    private fun initRecyclerView() {
        chatItemAdapter = ChatItemAdapter(emptyList())
        binding.rvChat.apply {
            adapter = chatItemAdapter
            layoutManager = LinearLayoutManager(this@ChatRoomActivity)
        }
    }

    private fun initListener() {
        binding.ivArrowLeft.setOnClickListener { finish() }
    }

    @SuppressLint("CheckResult")
    private fun connectStomp() {
        connected = true
        stompClient.connect()

        stompClient.topic("/room/${roomId}").subscribe {

            topicMessage ->
            Log.i("message Receive", topicMessage.payload)
            val message = Gson().fromJson(topicMessage.payload, ChatItem::class.java)
            chatList.add(message)
            runOnUiThread {
                binding.rvChat.adapter = ChatItemAdapter(chatList.toList())
                binding.rvChat.scrollToPosition(chatList.size-1)
            }
        }
    }
}