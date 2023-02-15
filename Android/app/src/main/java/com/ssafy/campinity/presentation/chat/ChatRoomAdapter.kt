package com.ssafy.campinity.presentation.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ItemChatRoomBinding
import com.ssafy.campinity.domain.entity.chat.RoomItem

class ChatRoomAdapter(private val onItemClicked: (roomId: String) -> Unit) :
    RecyclerView.Adapter<ChatRoomAdapter.RoomViewHolder>() {

    private var items: List<RoomItem> = listOf()
    lateinit var binding: ItemChatRoomBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_chat_room, parent, false
        )
        return RoomViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class RoomViewHolder(
        private val binding: ItemChatRoomBinding,
        private val onItemClicked: (roomId: String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: RoomItem) {
            binding.root.setOnClickListener {
                onItemClicked(data.roomId)
            }
        }
    }

    fun setChatRoom(roomItem: List<RoomItem>) {
        this.items = roomItem
        notifyDataSetChanged()
    }
}