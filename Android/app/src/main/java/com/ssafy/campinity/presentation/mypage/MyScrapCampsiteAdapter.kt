package com.ssafy.campinity.presentation.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ItemScrapCampingSiteBinding
import com.ssafy.campinity.domain.entity.mypage.ScrapCampsite

class MyScrapCampsiteAdapter(private val onItemClicked: (campsiteId: String) -> Unit) :
    RecyclerView.Adapter<CampingSiteViewHolder>() {

    private var items: List<ScrapCampsite> = listOf()
    lateinit var binding: ItemScrapCampingSiteBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampingSiteViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_scrap_camping_site,
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

    fun setCampsite(campsiteItem: List<ScrapCampsite>) {
        this.items = campsiteItem
        notifyDataSetChanged()
    }
}

class CampingSiteViewHolder(
    val binding: ItemScrapCampingSiteBinding,
    private val onItemClicked: (campsiteId: String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(data: ScrapCampsite) {
        binding.campsite = data
        binding.root.setOnClickListener {
            onItemClicked(data.campsiteId)
        }
    }
}