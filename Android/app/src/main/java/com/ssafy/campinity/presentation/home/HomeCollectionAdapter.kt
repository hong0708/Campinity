package com.ssafy.campinity.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ItemHomeCollectionBinding
import com.ssafy.campinity.domain.entity.home.HomeCollection

class HomeCollectionAdapter(private val datas: ArrayList<HomeCollection>) :
    RecyclerView.Adapter<CollectionViewHolder>() {

    lateinit var binding: ItemHomeCollectionBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_home_collection,
            parent,
            false
        )
        return CollectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        val viewHolder: CollectionViewHolder = holder
        viewHolder.onBind(datas[position])
    }

    override fun getItemCount(): Int = datas.size
}

class CollectionViewHolder(val binding: ItemHomeCollectionBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(datas: HomeCollection) {
        binding.ivHomeCollection.setImageResource(datas.img)
        binding.tvTitleHomeCollection.text = datas.title
        binding.tvDateHomeCollection.text = datas.date
    }
}