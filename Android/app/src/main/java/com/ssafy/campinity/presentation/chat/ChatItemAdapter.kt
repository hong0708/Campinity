package com.ssafy.campinity.presentation.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.ApplicationClass
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ItemChatBinding
import com.ssafy.campinity.domain.entity.chat.ChatItem

class ChatItemAdapter(var items: List<ChatItem>) :
    RecyclerView.Adapter<ChatItemAdapter.ChatViewHolder>() {

    lateinit var binding: ItemChatBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_chat, parent, false
        )
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ChatViewHolder(
        private val binding: ItemChatBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ChatItem) {
            if (data.sender == ApplicationClass.preferences.nickname) {
                binding.apply {
                    llOtherSide.visibility = View.GONE
                    llMySide.visibility = View.VISIBLE
                    tvMyName.text = data.sender
                    tvMyMessage.text = data.message
                    tvMyTime.text = data.timestamp?.substring(11, 16) ?: ""
                }
            } else {
                binding.apply {
                    llMySide.visibility = View.GONE
                    llOtherSide.visibility = View.VISIBLE
                    tvOtherName.text = data.sender
                    tvOtherMessage.text = data.message
                    tvOtherTime.text = data.timestamp?.substring(11, 16) ?: ""
                }
            }
        }
    }
}