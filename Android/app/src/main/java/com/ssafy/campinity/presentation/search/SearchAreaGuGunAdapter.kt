package com.ssafy.campinity.presentation.search

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.util.forEach
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ItemSearchAreaGugunBinding
import com.ssafy.campinity.domain.entity.search.AreaGugun

class SearchAreaGuGunAdapter(
    private val context: Context,
    private val gugun: List<AreaGugun>,
    private val toggleBtn: (String, Boolean) -> Unit
) :
    RecyclerView.Adapter<SearchAreaGuGunAdapter.ViewHolder>() {

    private lateinit var binding: ItemSearchAreaGugunBinding
    private var mSelectedItem = SparseBooleanArray(0)
    private val selectedItemCount: Int
        get() {
            var count = 0
            mSelectedItem.forEach { _, value -> if (value) count++ }
            return count
        }

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
        holder.initListener()
    }

    override fun getItemCount(): Int = gugun.size

    fun selectAll() {
        for (position in 0 until itemCount) {
            if (!mSelectedItem.get(position, false)) {
                mSelectedItem.put(position, true)
                notifyItemChanged(position)
            }
        }
    }

    fun unselectAll() {
        for (position in 0 until itemCount) {
            if (mSelectedItem.get(position, false)) {
                mSelectedItem.put(position, false)
                notifyItemChanged(position)
            }
        }
    }

    inner class ViewHolder(private val binding: ItemSearchAreaGugunBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AreaGugun) {
            binding.item = item
            binding.tvCampsiteCount.text =
                context.getString(R.string.content_campsite_count, item.campsiteCount)

            binding.root.apply {
                if (mSelectedItem.get(adapterPosition, false))
                    this.setBackgroundResource(
                        R.drawable.bg_rect_primrose_grey_alpha30_radius5_stroke1
                    )
                else
                    this.setBackgroundResource(
                        R.drawable.bg_rect_white_grey_alpha30_radius5_stroke1
                    )
            }
        }

        fun initListener() {
            binding.root.setOnClickListener {
                if (mSelectedItem.get(adapterPosition, false)) {
                    mSelectedItem.put(adapterPosition, false)
                    it.setBackgroundResource(
                        R.drawable.bg_rect_white_grey_alpha30_radius5_stroke1
                    )
                } else {
                    mSelectedItem.put(adapterPosition, true)
                    it.setBackgroundResource(
                        R.drawable.bg_rect_primrose_grey_alpha30_radius5_stroke1
                    )
                }

                if (selectedItemCount == itemCount)
                    toggleBtn("selectAll", true)
                else
                    toggleBtn("selectAll", false)

                if (selectedItemCount > 0)
                    toggleBtn("submit", true)
                else
                    toggleBtn("submit", false)
            }

        }
    }
}