package com.ssafy.campinity.presentation.collection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ItemCollectionCardBinding
import com.ssafy.campinity.domain.entity.collection.CollectionItem

class CardStackAdapter(
    private val onItemClicked: (collectionId: String) -> Unit
) : RecyclerView.Adapter<CardStackAdapter.CardViewHolder>() {

    private var items: List<CollectionItem> = listOf()
    lateinit var binding: ItemCollectionCardBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_collection_card, parent, false
        )
        return CardViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<CollectionItem>) {
        this.items = items
    }

    fun getItems(): List<CollectionItem> {
        return items
    }

    class CardViewHolder(
        private val binding: ItemCollectionCardBinding,
        private val onItemClicked: (collectionId: String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: CollectionItem) {
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
