package com.ssafy.campinity.presentation.collection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ItemCollectionCardBinding
import com.ssafy.campinity.domain.entity.collection.CollectionItem

class CardStackAdapter(
    private var cards: List<CollectionItem> = emptyList(),
    private val onItemClicked: (collectionId: String) -> Unit
) : RecyclerView.Adapter<CardStackAdapter.CardViewHolder>() {

    lateinit var binding: ItemCollectionCardBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_collection_card, parent, false
        )
        return CardViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.onBind(cards[position])
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    fun setSpots(spots: List<CollectionItem>) {
        this.cards = spots
    }

    fun getSpots(): List<CollectionItem> {
        return cards
    }

    class CardViewHolder(
        private val binding: ItemCollectionCardBinding,
        private val onItemClicked: (collectionId: String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: CollectionItem) {
            binding.ivCollection.setImageResource(data.img)
            binding.tvDateCollection.text = data.date
            binding.tvTitleCollection.text = data.title
            binding.root.setOnClickListener {
                onItemClicked(data.collectionId)
            }
        }
    }
}
