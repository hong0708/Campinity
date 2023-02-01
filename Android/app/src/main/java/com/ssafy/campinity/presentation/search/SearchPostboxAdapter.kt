package com.ssafy.campinity.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.databinding.ItemLetterListBinding
import com.ssafy.campinity.domain.entity.search.Letter

class SearchPostboxAdapter(private val letters: List<Letter>) :
    RecyclerView.Adapter<SearchPostboxAdapter.ViewHolder>() {

    private lateinit var binding: ItemLetterListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemLetterListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(letters[position])
    }

    override fun getItemCount(): Int = letters.size

    inner class ViewHolder(private val binding: ItemLetterListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Letter) {
            binding.tvNoteTitle.text = item.content
        }
    }
}