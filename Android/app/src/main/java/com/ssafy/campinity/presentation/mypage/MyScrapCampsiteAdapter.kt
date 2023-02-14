package com.ssafy.campinity.presentation.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ItemHomeCampingSiteBinding
import com.ssafy.campinity.domain.entity.home.RankingCampsiteItem

class MyScrapCampsiteAdapter(private val onItemClicked: (campsiteId: String) -> Unit) :
    RecyclerView.Adapter<CampingSiteViewHolder>() {

    private var items: List<RankingCampsiteItem> = listOf()
    lateinit var binding: ItemHomeCampingSiteBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampingSiteViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_home_camping_site,
            parent,
            false
        )
        return CampingSiteViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: CampingSiteViewHolder, position: Int) {
        val viewHolder: CampingSiteViewHolder = holder
        viewHolder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setCampsite(campsiteItem: List<RankingCampsiteItem>) {
        this.items = campsiteItem
        notifyDataSetChanged()
    }
}

class CampingSiteViewHolder(
    val binding: ItemHomeCampingSiteBinding,
    private val onItemClicked: (campsiteId: String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(data: RankingCampsiteItem) {
        binding.rankingCampsite = data
        binding.root.setOnClickListener {
            onItemClicked(data.campsiteId)
        }
    }
}