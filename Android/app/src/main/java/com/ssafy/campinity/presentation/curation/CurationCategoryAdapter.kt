package com.ssafy.campinity.presentation.curation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ItemCurationBinding
import com.ssafy.campinity.domain.entity.curation.CurationItem

class CurationCategoryAdapter(
    private val onItemClicked: (itemId: String) -> Unit
) : RecyclerView.Adapter<CurationCategoryAdapter.CurationViewHolder>() {

    private var items: List<CurationItem> = listOf()
    lateinit var binding: ItemCurationBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurationViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_curation, parent, false
        )
        return CurationViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: CurationViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class CurationViewHolder(
        private val binding: ItemCurationBinding,
        private val onItemClicked: (itemId: String) -> Unit
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