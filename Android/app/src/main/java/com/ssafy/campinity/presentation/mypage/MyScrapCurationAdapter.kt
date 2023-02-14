package com.ssafy.campinity.presentation.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ItemScrapCurationBinding
import com.ssafy.campinity.domain.entity.curation.CurationItem

class MyScrapCurationAdapter(
    private val onItemClicked: (curationId: String) -> Unit
) : RecyclerView.Adapter<MyScrapCurationAdapter.ScrapCurationViewHolder>() {

    private var items: List<CurationItem> = listOf()
    lateinit var binding: ItemScrapCurationBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrapCurationViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_scrap_curation,
            parent,
            false
        )
        return ScrapCurationViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: ScrapCurationViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ScrapCurationViewHolder(
        val binding: ItemScrapCurationBinding,
        private val onItemClicked: (curationId: String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: CurationItem) {
            binding.curation = data
            binding.root.setOnClickListener {
                onItemClicked(data.curationId)
            }
        }
    }

    fun setCuration(curationItem: List<CurationItem>) {
        this.items = curationItem
        notifyDataSetChanged()
    }
}

