package com.ssafy.campinity.presentation.collection

import androidx.recyclerview.widget.DiffUtil
import com.ssafy.campinity.domain.entity.collection.CollectionItem

class CollectionDiffCallback(
    private val old: List<CollectionItem>,
    private val new: List<CollectionItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return old.size
    }

    override fun getNewListSize(): Int {
        return new.size
    }

    override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition].collectionId == new[newPosition].collectionId
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition] == new[newPosition]
    }

}
