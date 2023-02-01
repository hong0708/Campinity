package com.ssafy.campinity.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.databinding.ItemCampsiteReviewBinding
import com.ssafy.campinity.domain.entity.search.Review

class CampsiteReviewAdapter(private val reviews: List<Review>) :
    RecyclerView.Adapter<CampsiteReviewAdapter.ViewHolder>() {

    private lateinit var binding: ItemCampsiteReviewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemCampsiteReviewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reviews[position])
    }

    override fun getItemCount(): Int = reviews.size

    inner class ViewHolder(binding: ItemCampsiteReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Review) {}
    }
}