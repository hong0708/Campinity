package com.ssafy.campinity.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.getDeviceWidthPx
import com.ssafy.campinity.databinding.ItemCampsiteDetailImageBinding

class CampsiteDetailImageAdapter(private val images: List<String>) :
    RecyclerView.Adapter<CampsiteDetailImageAdapter.ViewHolder>() {
    private lateinit var binding: ItemCampsiteDetailImageBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemCampsiteDetailImageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        binding.ivCampsiteImage.apply {
            if (images.isEmpty()) {
                Glide.with(context)
                    .load(R.drawable.bg_image_not_found)
                    .override(getDeviceWidthPx(context))
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(this)
            } else {
                Glide.with(context)
                    .load(images[position])
                    .override(getDeviceWidthPx(context))
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(this)
            }
        }
    }

    override fun getItemCount(): Int = if (images.isEmpty()) 1 else images.size

    inner class ViewHolder(binding: ItemCampsiteDetailImageBinding) :
        RecyclerView.ViewHolder(binding.root)
}
