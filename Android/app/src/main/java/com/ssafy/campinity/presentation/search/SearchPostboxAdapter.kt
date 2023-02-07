package com.ssafy.campinity.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.databinding.ItemLetterListBinding
import com.ssafy.campinity.domain.entity.community.NoteQuestionTitle

class SearchPostboxAdapter(
    private val onItemClickListener: (String) -> Unit
) :
    RecyclerView.Adapter<SearchPostboxAdapter.ViewHolder>() {

    private lateinit var binding: ItemLetterListBinding
    private var letters: List<NoteQuestionTitle> = listOf()

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

    fun setLetters(letters: List<NoteQuestionTitle>) {
        this.letters = letters
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemLetterListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NoteQuestionTitle) {
            binding.tvNoteTitle.text = item.content

            binding.root.setOnClickListener {
                onItemClickListener(item.questionId)
            }
        }
    }
}