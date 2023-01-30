package com.ssafy.campinity.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ItemSearchFilterCateforyBinding
import com.ssafy.campinity.domain.entity.search.FilterCategory

class SearchFilterCategoryAdapter(
    private val categories: List<FilterCategory>, private val toggleBtnSubmit: (Boolean) -> Unit
) : RecyclerView.Adapter<SearchFilterCategoryAdapter.ViewHolder>() {

    private lateinit var binding: ItemSearchFilterCateforyBinding
    private lateinit var searchFilterOptionAdapter: SearchFilterOptionAdapter
    private var selectedItemCount: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemSearchFilterCateforyBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size

    private fun addSelectedItemCount(count: Int) {
        selectedItemCount += count
    }

    private fun toggleSubmit() {
        if (selectedItemCount > 0) toggleBtnSubmit(true)
        else toggleBtnSubmit(false)
    }

    inner class ViewHolder(private val binding: ItemSearchFilterCateforyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FilterCategory) {
            binding.titleCatefory.text = item.category

            initListener(item)
        }

        fun initListener(item: FilterCategory) {
            binding.rvCategory.apply {
                layoutManager = LinearLayoutManager(
                    context, LinearLayoutManager.HORIZONTAL, false
                )

                searchFilterOptionAdapter = SearchFilterOptionAdapter(
                    item.options,
                    this@SearchFilterCategoryAdapter::addSelectedItemCount,
                    this@SearchFilterCategoryAdapter::toggleSubmit
                )
                adapter = searchFilterOptionAdapter

                addItemDecoration(DividerItemDecoration(
                    context, DividerItemDecoration.HORIZONTAL
                ).apply {
                    setDrawable(
                        ContextCompat.getDrawable(
                            context, R.drawable.bg_rect_transparent_width7
                        )!!
                    )
                })
            }
        }
    }
}