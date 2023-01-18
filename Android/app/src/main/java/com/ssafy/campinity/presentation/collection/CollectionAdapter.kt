package com.ssafy.campinity.presentation.collection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ItemCollectionBinding
import com.ssafy.campinity.domain.entity.collection.CollectionItem

class CollectionAdapter(private val onItemClicked: (collectionId: String) -> Unit) :
    RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder>() {

    private var items: List<CollectionItem> = listOf()
    lateinit var binding: ItemCollectionBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_collection, parent, false
        )
        return CollectionViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class CollectionViewHolder(
        private val binding: ItemCollectionBinding,
        private val onItemClicked: (collectionId: String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: CollectionItem) {
            binding.collection = data
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