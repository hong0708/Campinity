package com.ssafy.campinity.presentation.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ssafy.campinity.common.util.getDeviceWidthPx
import com.ssafy.campinity.databinding.ItemCampsiteDetailImageBinding
import kotlin.reflect.KFunction1

class CampsiteDetailImageAdapter(
    private val context: Context,
    private val thumbnails: List<String>,
    private val detailImages: List<String>,
    private val onImgClicked: (imgUri: String) -> Unit
) : RecyclerView.Adapter<CampsiteDetailImageAdapter.ViewHolder>() {

    private lateinit var binding: ItemCampsiteDetailImageBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemCampsiteDetailImageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        binding.ivCampsiteImage.let {
            Glide.with(it.context)
                .load("http://i8d101.p.ssafy.io:8003/images${thumbnails[position]}")
                .override(getDeviceWidthPx(context))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(it)
        }

        binding.ivCampsiteImage.setOnClickListener {
            onImgClicked(detailImages[position])
        }

        if (position <= thumbnails.size) {
            val endPosition = if (position + 2 > thumbnails.size) thumbnails.size else position + 2

            thumbnails.subList(position, endPosition).map { it }.forEach {
                Glide.with(context)
                    .load(it)
                    .preload()
            }
        }
    }

    override fun getItemCount(): Int = thumbnails.size

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    inner class ViewHolder(binding: ItemCampsiteDetailImageBinding) :
        RecyclerView.ViewHolder(binding.root)
}
