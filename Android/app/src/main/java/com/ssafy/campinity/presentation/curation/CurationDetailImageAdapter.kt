package com.ssafy.campinity.presentation.curation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.getDeviceWidthPx
import com.ssafy.campinity.databinding.ItemCurationDetailBinding

class CurationDetailImageAdapter(private val images: List<String>) :
    RecyclerView.Adapter<CurationViewHolder>() {

    lateinit var binding: ItemCurationDetailBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurationViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_curation_detail,
            parent,
            false
        )
        return CurationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurationViewHolder, position: Int) {
        binding.ivCurationDetail.apply {
            if (images.isEmpty()) {
                Glide.with(context)
                    .load(R.drawable.bg_image_not_found)
                    .override(getDeviceWidthPx(context))
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(this)
            } else {
                Glide.with(context)
                    .load("http://i8d101.p.ssafy.io:8003/images" + images[position])
                    .override(getDeviceWidthPx(context))
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(this)
            }
        }
    }

    override fun getItemCount(): Int = if (images.isEmpty()) 1 else images.size
}

class CurationViewHolder(val binding: ItemCurationDetailBinding) :
    RecyclerView.ViewHolder(binding.root)