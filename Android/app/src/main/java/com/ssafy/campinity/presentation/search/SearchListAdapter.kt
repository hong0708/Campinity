package com.ssafy.campinity.presentation.search

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.databinding.ItemSearchListBinding
import com.ssafy.campinity.domain.entity.search.CampsiteBriefInfo

class SearchListAdapter(
    private val context: Context, private val campsites: ArrayList<CampsiteBriefInfo>
) : RecyclerView.Adapter<SearchListAdapter.SearchListViewHolder>() {
    class SearchListViewHolder(private val binding: ItemSearchListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            TODO("Not yet implemented")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: SearchListViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = campsites.size
}