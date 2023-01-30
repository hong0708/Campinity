package com.ssafy.campinity.presentation.search

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ItemSearchFilterOptionBinding

class SearchFilterOptionAdapter(
    private val options: List<String>,
    private val addSelectedItemCount: (Int) -> Unit,
    private val toggleSubmit: () -> Unit,
) : RecyclerView.Adapter<SearchFilterOptionAdapter.ViewHolder>() {

    private lateinit var binding: ItemSearchFilterOptionBinding
    private val mSelectedItem = SparseBooleanArray(0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemSearchFilterOptionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(options[position])
    }

    override fun getItemCount(): Int = options.size

    fun unSelectAll() {
        for (position in 0 until itemCount)
            if (mSelectedItem.get(position, false)) {
                mSelectedItem.put(position, false)
                binding.root.setBackgroundResource(
                    R.drawable.bg_rect_white_grey_alpha30_radius10_stroke1
                )
            }
    }

    inner class ViewHolder(private val binding: ItemSearchFilterOptionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.tvFilter.text = item

            initListener()
        }

        fun initListener() {
            binding.root.setOnClickListener {
                if (mSelectedItem.get(adapterPosition, false)) {
                    mSelectedItem.put(adapterPosition, false)
                    it.setBackgroundResource(R.drawable.bg_rect_white_grey_alpha30_radius10_stroke1)
                    addSelectedItemCount(-1)
                } else {
                    mSelectedItem.put(adapterPosition, true)
                    it.setBackgroundResource(
                        R.drawable.bg_rect_primrose_grey_alpha30_radius10_stroke1
                    )
                    addSelectedItemCount(1)
                }

                toggleSubmit()
            }
        }
    }
}