package com.ssafy.campinity.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ItemHomeBannerBinding
import com.ssafy.campinity.domain.entity.home.HomeBanner

class HomeBannerAdapter(private val onItemClicked: (curationId: String) -> Unit) :
    RecyclerView.Adapter<HomeBannerAdapter.BannerViewHolder>() {

    private var items: ArrayList<HomeBanner> = arrayListOf()
    lateinit var binding: ItemHomeBannerBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_home_banner,
            parent,
            false
        )
        return BannerViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class BannerViewHolder(
        val binding: ItemHomeBannerBinding,
        private val onItemClicked: (curationId: String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: HomeBanner) {
            binding.homeBanner = data
            binding.root.setOnClickListener {
                onItemClicked(data.curationId)
            }
        }
    }

    fun addHomeBanner(item: List<HomeBanner>) {
        for (i in item) {
            this.items.add(i)
        }
        notifyItemInserted(this.items.size)
    }
}

