package com.ssafy.campinity.presentation.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.Size
import com.ssafy.campinity.common.util.glide
import com.ssafy.campinity.common.util.preload
import com.ssafy.campinity.common.util.px
import com.ssafy.campinity.databinding.ItemCampsiteBriefImageBinding

class CampsiteBriefImageAdapter(private val context: Context, private val images: List<String>) :
    RecyclerView.Adapter<CampsiteBriefImageAdapter.ViewHolder>() {

    private lateinit var binding: ItemCampsiteBriefImageBinding

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemCampsiteBriefImageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        binding.ivCampsiteImage.let {
            if (images.isEmpty())
                glide(
                    it.context,
                    R.drawable.bg_image_not_found,
                    Size(120.px(context), 150.px(context)),
                    null
                ).centerCrop().into(it)
            else
                glide(
                    it.context,
                    images[position],
                    Size(120.px(context), 150.px(context)),
                    R.drawable.bg_image_not_found
                ).centerCrop().into(it)
        }

        if (images.isNotEmpty() && position <= itemCount) {
            val endPosition =
                if (position + 6 > itemCount)
                    itemCount
                else
                    position + 6

            images.subList(position, endPosition).map { it }.forEach {
                preload(context, it, Size(120.px(context), 150.px(context)))
            }
        }
    }

    override fun getItemCount(): Int = if (images.isEmpty()) 1 else images.size

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    inner class ViewHolder(binding: ItemCampsiteBriefImageBinding) :
        RecyclerView.ViewHolder(binding.root)
}