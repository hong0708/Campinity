package com.ssafy.campinity.presentation.search

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ItemSearchAreaGugunBinding
import com.ssafy.campinity.domain.entity.search.AreaGugun

class SearchAreaGuGunAdapter(private val gugun: List<AreaGugun>) :
    RecyclerView.Adapter<SearchAreaGuGunAdapter.ViewHolder>() {
    private lateinit var binding: ItemSearchAreaGugunBinding
    private var mSelectedItem = SparseBooleanArray(0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemSearchAreaGugunBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(gugun[position])
    }

    override fun getItemCount(): Int = gugun.size

    fun selectAll() {
        for (position in 0 until itemCount) {
            mSelectedItem.put(position, true)
        }
        notifyItemRangeChanged(0, itemCount)
    }

    fun unselectAll() {
        for (position in 0 until itemCount) {
            mSelectedItem.put(position, false)
        }
        notifyItemRangeChanged(0, itemCount)
    }

    inner class ViewHolder(private val binding: ItemSearchAreaGugunBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AreaGugun) {
            binding.item = item
            binding.root.apply {
                setOnClickListener {
                    if (mSelectedItem.get(adapterPosition, false)) {
                        mSelectedItem.put(adapterPosition, false)
                        this.setBackgroundResource(R.drawable.bg_rect_white_grey_alpha30_radius5_stroke1)
                    } else {
                        mSelectedItem.put(adapterPosition, true)
                        this.setBackgroundResource(R.drawable.bg_rect_white_smoke_white_stroke1)
                    }
                }

                if (mSelectedItem.get(adapterPosition, false)) {
                    this.setBackgroundResource(R.drawable.bg_rect_white_smoke_white_stroke1)
                } else {
                    this.setBackgroundResource(R.drawable.bg_rect_white_grey_alpha30_radius5_stroke1)
                }
            }
        }
    }
}