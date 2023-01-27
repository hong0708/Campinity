package com.ssafy.campinity.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.databinding.ItemCampsiteFacilityAndLeisureBinding
import com.ssafy.campinity.domain.entity.search.FacilityAndLeisureItem

class CampsiteFacilityAndLeisureAdapter(private val items: List<FacilityAndLeisureItem>) :
    RecyclerView.Adapter<CampsiteFacilityAndLeisureAdapter.ViewHolder>() {
    private lateinit var binding: ItemCampsiteFacilityAndLeisureBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemCampsiteFacilityAndLeisureBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: ItemCampsiteFacilityAndLeisureBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FacilityAndLeisureItem) {
            binding.ivFacilityAndLeisure.setImageResource(item.imageId)
        }
    }
}