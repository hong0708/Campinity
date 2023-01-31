package com.ssafy.campinity.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.databinding.ItemSearchListBinding
import com.ssafy.campinity.domain.entity.search.CampsiteBriefInfo

class SearchListAdapter(
    private val campsites: List<CampsiteBriefInfo>,
    private val navigationToCampsiteDetailFragment: (String) -> Unit,
    private val navigationToSearchPostboxFragment: () -> Unit
) : RecyclerView.Adapter<SearchListAdapter.SearchListViewHolder>() {

    private lateinit var binding: ItemSearchListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListViewHolder {
        binding = ItemSearchListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchListViewHolder, position: Int) {
        holder.bind(campsites[position])

        holder.initListener()

        binding.rvCampsiteImage.apply {
            layoutManager = LinearLayoutManager(
                binding.rvCampsiteImage.context,
                RecyclerView.HORIZONTAL,
                false
            )
            adapter = CampsiteBriefImageAdapter(campsites[position].images)
        }
    }

    override fun getItemCount(): Int = campsites.size

    inner class SearchListViewHolder(private val binding: ItemSearchListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CampsiteBriefInfo) {
            binding.item = item
        }

        fun initListener() {
            binding.root.setOnClickListener {
//                navigationToCampsiteDetailFragment(item.campsiteId)
            }
        }
    }
}