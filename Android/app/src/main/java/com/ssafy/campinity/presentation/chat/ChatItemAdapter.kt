package com.ssafy.campinity.presentation.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ItemChatBinding
import com.ssafy.campinity.domain.entity.chat.ChatItem

class ChatItemAdapter : RecyclerView.Adapter<ChatItemAdapter.ChatViewHolder>() {

    private var items: List<ChatItem> = listOf()
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

        }
    }

    fun setChatItem(chatItem: List<ChatItem>) {
        this.items = chatItem
        notifyDataSetChanged()
    }
}