package com.ssafy.campinity.presentation.search

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.campinity.databinding.ItemCampsiteImageBinding


class CampsiteImageAdapter(private val context: Context, private val campsiteImages: Array<Uri>) :
    RecyclerView.Adapter<CampsiteImageAdapter.ViewPagerHolder>() {
    private lateinit var binding: ItemCampsiteImageBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        binding =
            ItemCampsiteImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
        holder.apply {
            Glide.with(context).load(campsiteImages[position]).into(binding.ivCampingSite)
        }
    }

    override fun getItemCount() = campsiteImages.size

    class ViewPagerHolder(binding: ItemCampsiteImageBinding) :
        RecyclerView.ViewHolder(binding.root) {}
}