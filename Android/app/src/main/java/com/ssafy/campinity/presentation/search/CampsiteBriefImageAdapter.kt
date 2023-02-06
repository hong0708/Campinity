package com.ssafy.campinity.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.px
import com.ssafy.campinity.databinding.ItemCampsiteBriefImageBinding

class CampsiteBriefImageAdapter(private val images: List<String>) :
    RecyclerView.Adapter<CampsiteBriefImageAdapter.SearchImageViewHolder>() {

    private lateinit var binding: ItemCampsiteBriefImageBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchImageViewHolder {
        binding = ItemCampsiteBriefImageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SearchImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchImageViewHolder, position: Int) {
        if (images.isEmpty()) {
            Glide.with(binding.ivCampsiteImage.context)
                .load(R.drawable.bg_image_not_found)
                .override(
                    120.px(binding.ivCampsiteImage.context), 150.px(binding.ivCampsiteImage.context)
                )
                .centerCrop()
                .placeholder(R.drawable.bg_image_not_found)
                .error(R.drawable.bg_image_not_found)
                .fallback(R.drawable.bg_image_not_found)
                .into(binding.ivCampsiteImage)
        } else {
            Glide.with(binding.ivCampsiteImage.context)
                .load(images[position])
                .override(
                    120.px(binding.ivCampsiteImage.context), 150.px(binding.ivCampsiteImage.context)
                )
                .centerCrop()
                .placeholder(R.drawable.bg_image_not_found)
                .error(R.drawable.bg_image_not_found)
                .fallback(R.drawable.bg_image_not_found)
                .into(binding.ivCampsiteImage)
        }
    }

    override fun getItemCount(): Int = if (images.isEmpty()) 1 else images.size

    inner class SearchImageViewHolder(binding: ItemCampsiteBriefImageBinding) :
        RecyclerView.ViewHolder(binding.root)
}