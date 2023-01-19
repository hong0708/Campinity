package com.ssafy.campinity.presentation.collection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ItemCollectionGridBinding
import com.ssafy.campinity.domain.entity.collection.CollectionItem

class GridAdapter(private val onItemClicked: (collectionId: String) -> Unit) :
    RecyclerView.Adapter<GridAdapter.GridViewHolder>() {

    private var items: List<CollectionItem> = listOf()
    lateinit var binding: ItemCollectionGridBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_collection_grid, parent, false
        )
        return GridViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class GridViewHolder(
        private val binding: ItemCollectionGridBinding,
        private val onItemClicked: (collectionId: String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: CollectionItem) {
            binding.collectionGrid = data
            binding.root.setOnClickListener {
                onItemClicked(data.collectionId)
            }
        }
    }

    fun setCollection(collectionItem: List<CollectionItem>) {
        this.items = collectionItem
        notifyDataSetChanged()
    }
}
