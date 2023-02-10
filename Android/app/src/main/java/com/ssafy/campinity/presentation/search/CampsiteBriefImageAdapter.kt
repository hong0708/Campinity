package com.ssafy.campinity.presentation.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ssafy.campinity.R
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
                Glide.with(it.context)
                    .load(R.drawable.bg_image_not_found)
                    .override(120.px(context), 150.px(context))
                    .centerCrop()
                    .into(it)
            else
                Glide.with(it.context)
                    .load(images[position])
                    .override(120.px(context), 150.px(context))
                    .centerCrop()
                    .placeholder(R.drawable.bg_image_not_found)
                    .error(R.drawable.bg_image_not_found)
                    .fallback(R.drawable.bg_image_not_found)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(it)
        }

        if (images.isNotEmpty() && position <= itemCount) {
            val endPosition = if (position + 6 > itemCount) itemCount else position + 6

            images.subList(position, endPosition).map { it }.forEach {
                Glide.with(context)
                    .load(it)
                    .preload(120.px(context), 150.px(context))
            }
        }
    }

    override fun getItemCount(): Int = if (images.isEmpty()) 1 else images.size

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    inner class ViewHolder(binding: ItemCampsiteBriefImageBinding) :
        RecyclerView.ViewHolder(binding.root)
}