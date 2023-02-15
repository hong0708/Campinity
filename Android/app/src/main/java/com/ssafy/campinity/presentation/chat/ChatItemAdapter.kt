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
                binding.llOtherSide.visibility = View.GONE
                binding.llMySide.visibility = View.VISIBLE
                binding.tvMyName.text = data.sender
                binding.tvMyMessage.text = data.message
                binding.tvMyTime.text = data.time?.substring(11, 16) ?: ""
            } else {
                binding.llMySide.visibility = View.GONE
                binding.llOtherSide.visibility = View.VISIBLE
                binding.tvOtherName.text = data.sender
                binding.tvOtherMessage.text = data.message
                binding.tvOtherTime.text = data.time?.substring(11, 16) ?: ""
            }
        }
    }
}