package com.ssafy.campinity.presentation.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.common.util.*
import com.ssafy.campinity.databinding.ItemCampsiteDetailImageBinding

class CampsiteDetailImageAdapter(private val context: Context, private val images: List<String>) :
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
            if (images.isEmpty())
                imageNotFound(context, getDeviceWidthPx(context), this)
            else
                glide(context, images[position], getDeviceWidthPx(context), this)
        }
        if (images.isNotEmpty() && position <= images.size) {
            val endPosition =
                if (position + 2 > images.size)
                    images.size
                else
                    position + 2

            images.subList(position, endPosition).map { it }.forEach {
                preload(context, it, 120.px(context), 150.px(context))
            }
        }
    }

    override fun getItemCount(): Int = if (images.isEmpty()) 1 else images.size

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    inner class ViewHolder(binding: ItemCampsiteDetailImageBinding) :
        RecyclerView.ViewHolder(binding.root)
}
