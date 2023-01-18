package com.ssafy.campinity.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ItemHomeBannerBinding
import com.ssafy.campinity.domain.entity.home.HomeBanner

class HomeBannerAdapter(private val datas: ArrayList<HomeBanner>) :
    RecyclerView.Adapter<BannerViewHolder>() {

    lateinit var binding: ItemHomeBannerBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_home_banner,
            parent,
            false
        )
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val viewHolder: BannerViewHolder = holder
        viewHolder.onBind(datas[position % datas.size])
    }

    override fun getItemCount(): Int = Int.MAX_VALUE
}

class BannerViewHolder(val binding: ItemHomeBannerBinding) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(datas: HomeBanner) {
        binding.ivHomeBanner.setImageResource(datas.img)
        binding.tvHomeBanner.text = datas.title
    }
}