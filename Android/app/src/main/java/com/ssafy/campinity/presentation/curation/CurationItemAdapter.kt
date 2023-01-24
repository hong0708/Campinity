package com.ssafy.campinity.presentation.curation

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.databinding.ItemCurationBinding

class CurationItemAdapter(
    private val onItemClicked: (itemId: String) -> Unit
) : RecyclerView.Adapter<CurationItemAdapter.CurationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurationViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: CurationViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class CurationViewHolder(
        private val binding: ItemCurationBinding,
        private val onItemClicked: (itemId: String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: CurationData) {
            binding.curation = data
            binding.root.setOnClickListener {
                onItemClicked(data.curationId)
            }
        }
    }
}