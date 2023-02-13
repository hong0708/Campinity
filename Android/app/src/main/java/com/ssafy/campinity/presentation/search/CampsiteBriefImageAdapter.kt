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

class CampsiteBriefImageAdapter(
    private val context: Context,
    private val thumbnails: List<String>
) :
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
            Glide.with(it.context)
                .load("http://i8d101.p.ssafy.io:8003/images${thumbnails[position]}")
                .override(120.px(context), 150.px(context))
                .centerCrop()
                .placeholder(R.drawable.bg_image_not_found)
                .error(R.drawable.bg_image_not_found)
                .fallback(R.drawable.bg_image_not_found)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(it)
        }

        if (position <= itemCount) {
            val endPosition = if (position + 6 > itemCount) itemCount else position + 6

            thumbnails.subList(position, endPosition).map { it }.forEach {
                Glide.with(context)
                    .load(it)
                    .preload(120.px(context), 150.px(context))
            }
        }
    }

    override fun getItemCount(): Int = thumbnails.size

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    inner class ViewHolder(binding: ItemCampsiteBriefImageBinding) :
        RecyclerView.ViewHolder(binding.root)
}