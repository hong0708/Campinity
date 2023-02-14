package com.ssafy.campinity.presentation.search

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ItemSearchListBinding
import com.ssafy.campinity.domain.entity.search.CampsiteBriefInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchListAdapter(
    private val context: Context,
    private var campsites: List<CampsiteBriefInfo>,
    private val onCampsiteClickListener: (Int, String) -> Unit,
    private val navigationToSearchPostboxFragment: (String) -> Unit,
    private val scrapCampsite: suspend (Int, String) -> String
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
        with(campsites[position]) {
            holder.bind(this)
            holder.initListener(this)
            holder.initRecyclerView()
        }
    }

    override fun getItemCount(): Int = campsites.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<CampsiteBriefInfo>) {
        campsites = data
        notifyDataSetChanged()
    }

    inner class SearchListViewHolder(private val binding: ItemSearchListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CampsiteBriefInfo) {
            binding.item = item

            if (item.isScraped)
                binding.btnBookmark.setBackgroundResource(R.drawable.ic_bookmark_on)
            else
                binding.btnBookmark.setBackgroundResource(R.drawable.ic_bookmark_off)

            if (item.thumbnails.isEmpty()) {
                binding.rvCampsiteImage.visibility = View.GONE
                binding.clEmptyCollection.visibility = View.VISIBLE
            } else {
                binding.rvCampsiteImage.visibility = View.VISIBLE
                binding.clEmptyCollection.visibility = View.GONE
            }
        }

        fun initListener(item: CampsiteBriefInfo) {
            binding.btnPostbox.setOnClickListener {
                navigationToSearchPostboxFragment(item.campsiteId)
            }

            binding.btnBookmark.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    val isScraped = scrapCampsite(adapterPosition, item.campsiteId)
                    if (isScraped == "true")
                        binding.btnBookmark.setBackgroundResource(R.drawable.ic_bookmark_on)
                    else if (isScraped == "false")
                        binding.btnBookmark.setBackgroundResource(R.drawable.ic_bookmark_off)
                }
            }

            binding.rlCampsite.setOnClickListener {
                onCampsiteClickListener(adapterPosition, item.campsiteId)
            }
        }

        fun initRecyclerView() {
            binding.rvCampsiteImage.layoutManager = LinearLayoutManager(
                binding.rvCampsiteImage.context,
                RecyclerView.HORIZONTAL,
                false
            )
            binding.rvCampsiteImage.adapter =
                CampsiteBriefImageAdapter(context, campsites[adapterPosition].thumbnails).apply {
                    binding.rvCampsiteImage.setHasFixedSize(true)
                }
        }
    }
}