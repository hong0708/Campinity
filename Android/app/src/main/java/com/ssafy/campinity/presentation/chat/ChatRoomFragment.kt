package com.ssafy.campinity.presentation.chat

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.navArgs
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentChattingRoomBinding
import com.ssafy.campinity.domain.entity.chat.ChatItem
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompHeader

@AndroidEntryPoint
class ChatRoomFragment :
    BaseFragment<FragmentChattingRoomBinding>(R.layout.fragment_chatting_room) {

    private lateinit var callback: OnBackPressedCallback
    private val args by navArgs<ChatRoomFragmentArgs>()
    private val chatItemAdapter by lazy { ChatItemAdapter() }

    private val chatList: MutableList<ChatItem> = mutableListOf()

    private val url = "ws://i8d101.p.ssafy.io:8005/chat/websocket"
    private val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)
    private var connected = false

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

    @SuppressLint("CheckResult")
    private fun runStomp() {
        stompClient.topic("/pub/chat/${args.roomId}").subscribe { topicMessage ->
            Log.i("message Recieve", topicMessage.payload)
        }

        val headerList = arrayListOf<StompHeader>()
        headerList.add(StompHeader("inviteCode", "test0912"))
        headerList.add(StompHeader("positionType", "1"))
        stompClient.connect(headerList)

        stompClient.lifecycle().subscribe { lifecycleEvent ->
            when (lifecycleEvent.type) {
                LifecycleEvent.Type.OPENED -> {
                    Log.i("OPEND", "!!")
                }
                LifecycleEvent.Type.CLOSED -> {
                    Log.i("CLOSED", "!!")
                }
                LifecycleEvent.Type.ERROR -> {
                    Log.i("ERROR", "!!")
                    Log.e("CONNECT ERROR", lifecycleEvent.exception.toString())
                }
                else -> {
                    Log.i("ELSE", lifecycleEvent.message)
                }
            }
        }

        val data = JSONObject()
        data.put("positionType", "1")
        data.put("content", "test")
        data.put("messageType", "CHAT")
        data.put("destRoomCode", "test0912")

        stompClient.send("/stream/chat/send", data.toString()).subscribe()
    }
}