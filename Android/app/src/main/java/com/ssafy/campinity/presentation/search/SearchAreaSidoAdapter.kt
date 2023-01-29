package com.ssafy.campinity.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ItemSearchAreaSidoBinding

class SearchAreaSidoAdapter(private val sido: List<String>) :
    RecyclerView.Adapter<SearchAreaSidoAdapter.ViewHolder>() {
    private lateinit var binding: ItemSearchAreaSidoBinding
    private var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemSearchAreaSidoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sido[position])
    }

    override fun getItemCount(): Int = sido.size

    inner class ViewHolder(private val binding: ItemSearchAreaSidoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.item = item
            binding.root.apply {
                setOnClickListener {
                    notifyItemChanged(selectedPosition)
                    selectedPosition = adapterPosition
                    notifyItemChanged(selectedPosition)
                }

                if (selectedPosition == adapterPosition) {
                    this.setBackgroundResource(R.drawable.bg_rect_white_smoke_white_stroke1)
                } else {
                    this.setBackgroundResource(R.drawable.bg_rect_gainsboro_smoke_white_stroke1)
                }
            }
        }
    }
}