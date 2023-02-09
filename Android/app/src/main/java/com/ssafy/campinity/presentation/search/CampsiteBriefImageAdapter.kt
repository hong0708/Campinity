package com.ssafy.campinity.presentation.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.common.util.glide
import com.ssafy.campinity.common.util.imageNotFound
import com.ssafy.campinity.common.util.preload
import com.ssafy.campinity.common.util.px
import com.ssafy.campinity.databinding.ItemCampsiteBriefImageBinding

class CampsiteBriefImageAdapter(private val context: Context, private val images: List<String>) :
    RecyclerView.Adapter<CampsiteBriefImageAdapter.SearchImageViewHolder>() {

    private lateinit var binding: ItemCampsiteBriefImageBinding

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchImageViewHolder {
        binding = ItemCampsiteBriefImageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SearchImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchImageViewHolder, position: Int) {
        binding.ivCampsiteImage.apply {
            if (images.isEmpty())
                imageNotFound(context, 120.px(context), this)
            else
                glide(context, images[position], 120.px(context), 150.px(context), this)
        }
        if (images.isNotEmpty() && position <= itemCount) {
            val endPosition =
                if (position + 6 > itemCount)
                    itemCount
                else
                    position + 6

            images.subList(position, endPosition).map { it }.forEach {
                preload(context, it, 120.px(context), 150.px(context))
            }
        }
    }

    override fun getItemCount(): Int = if (images.isEmpty()) 1 else images.size

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    inner class SearchImageViewHolder(binding: ItemCampsiteBriefImageBinding) :
        RecyclerView.ViewHolder(binding.root)
}