package com.ssafy.campinity.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ItemHomeCampingSiteBinding

class HomeCampingSiteAdapter(private val datas: ArrayList<HomeCampingSite>) : RecyclerView.Adapter<CampingSiteViewHolder>() {

    lateinit var binding: ItemHomeCampingSiteBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampingSiteViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_home_camping_site, parent, false)
        return CampingSiteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CampingSiteViewHolder, position: Int) {
        val viewHolder: CampingSiteViewHolder = holder
        viewHolder.onBind(datas[position])
    }

    override fun getItemCount(): Int = datas.size
}

class CampingSiteViewHolder(val binding: ItemHomeCampingSiteBinding) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(datas: HomeCampingSite) {
        binding.ivHomeCampingSite.setImageResource(datas.img)
        binding.tvTitleHomeCampingSite.text = datas.title
        binding.tvLocationHomeCampingSite.text = datas.location
    }
}