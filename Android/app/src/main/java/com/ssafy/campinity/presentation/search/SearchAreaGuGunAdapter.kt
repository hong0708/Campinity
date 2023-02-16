package com.ssafy.campinity.presentation.search

import android.annotation.SuppressLint
import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.util.forEach
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.px
import com.ssafy.campinity.databinding.ItemSearchAreaGugunBinding
import com.ssafy.campinity.domain.entity.search.GugunItem

class SearchAreaGuGunAdapter(
    private val context: Context,
    private var gugun: List<GugunItem>,
    private var selectedGugunList: List<String>,
    private val btnWidth: Int,
    private val toggleSelectAllBtn: (Boolean) -> Unit
) : RecyclerView.Adapter<SearchAreaGuGunAdapter.ViewHolder>() {

    private lateinit var binding: ItemSearchAreaGugunBinding
    private var mSelectedItem = SparseBooleanArray(0)
    private val selectedItemCount: Int
        get() {
            var count = 0
            mSelectedItem.forEach { _, isSelected -> if (isSelected) count++ }
            return count
        }
    val selectedItems: List<String>
        get() {
            val items = mutableListOf<String>()

            mSelectedItem.forEach { position, isSelected ->
                if (isSelected) items.add(gugun[position].gugunName)
            }

            return items
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemSearchAreaGugunBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        initSelected()
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(gugun[position])
        holder.initListener()
    }

    override fun getItemCount(): Int = gugun.size

    @SuppressLint("NotifyDataSetChanged")
    fun setGugun(gugun: List<GugunItem>) {
        this.gugun = gugun
        initSelected()
        toggleSelectAllBtn()
        notifyDataSetChanged()
    }

    fun setSelectedGugun(selectedGugun: List<String>) {
        this.selectedGugunList = selectedGugun
    }

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
        toggleSelectAllBtn()
    }

    fun toggleSelectAllBtn() {
        if (selectedItemCount == itemCount) toggleSelectAllBtn(true)
        else toggleSelectAllBtn(false)
    }

    private fun initSelected() {
        selectedGugunList.forEach { selectedGugun ->
            gugun.forEachIndexed { index, gugunItem ->
                if (selectedGugun == gugunItem.gugunName) {
                    mSelectedItem.put(index, true)
                }
            }
        }
    }

    inner class ViewHolder(private val binding: ItemSearchAreaGugunBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GugunItem) {
            binding.llGugun.layoutParams =
                LinearLayout.LayoutParams(btnWidth.px(context), 33.px(context))

            binding.item = item
            binding.tvCampsiteCount.text =
                context.getString(R.string.content_campsite_count, item.campsiteCount)

            binding.root.apply {
                if (mSelectedItem.get(adapterPosition, false))
                    this.setBackgroundResource(
                        R.drawable.bg_rect_primrose_grey_alpha30_radius5_stroke1
                    )
                else this.setBackgroundResource(
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

                toggleSelectAllBtn()
            }
        }
    }
}