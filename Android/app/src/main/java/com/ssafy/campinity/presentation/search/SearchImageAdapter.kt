package com.ssafy.campinity.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.dp
import com.ssafy.campinity.databinding.ItemCampsiteImageBinding

class SearchImageAdapter(private val images: ArrayList<String>) :
    RecyclerView.Adapter<SearchImageAdapter.SearchImageViewHolder>() {
    private lateinit var binding: ItemCampsiteImageBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchImageViewHolder {
        binding = ItemCampsiteImageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return SearchImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int = images.size

    inner class SearchImageViewHolder(private val binding: ItemCampsiteImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(url: String) {
            Glide.with(binding.ivCampsiteImage.context)
                .load(url)
                .override(
                    220.dp(binding.ivCampsiteImage.context),
                    250.dp(binding.ivCampsiteImage.context)
                )
                .centerCrop()
                .placeholder(R.drawable.bg_image_not_found)
                .error(R.drawable.bg_image_not_found)
                .fallback(R.drawable.bg_image_not_found)
                .into(binding.ivCampsiteImage)
        }
    }
}